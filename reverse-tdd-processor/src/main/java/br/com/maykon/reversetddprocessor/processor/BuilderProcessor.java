package br.com.maykon.reversetddprocessor.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("br.com.maykon.reversetddprocessor.annotation.BuilderProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement element : annotations) {
      final Set<? extends Element> elementsAnnotatedWith =
          roundEnv.getElementsAnnotatedWith(element);

      Map<Boolean, List<Element>> annotatedMethods =
          elementsAnnotatedWith.stream()
              .collect(Collectors.partitioningBy(this::hasOneParameterAndStartWithSet));

      List<Element> setters = annotatedMethods.get(true);
      List<Element> otherMethods = annotatedMethods.get(false);

      otherMethods.forEach(
          it ->
              printMessage(
                  Diagnostic.Kind.ERROR,
                  "@BuilderProperty must be applied to a setXxx method with a single argument",
                  it));

      if (setters.isEmpty()) {
        continue;
      }

      TypeElement targetClass = ((TypeElement) setters.get(0).getEnclosingElement());

      Map<String, TypeMirror> setterMap =
          setters.stream()
              .collect(
                  Collectors.toMap(
                      setter -> setter.getSimpleName().toString(),
                      setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0)));

      try {
        writeBuilderFile(targetClass, setterMap);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  private void writeBuilderFile(TypeElement targetClassType, Map<String, TypeMirror> setterMethodsMap)
      throws IOException {
    final var targetClassName = targetClassType.getQualifiedName().toString();
    String packageName = "";
    int lastDot = targetClassName.lastIndexOf('.');
    if (lastDot > 0) {
      packageName = targetClassName.substring(0, lastDot);
    }

    String builderClassName = targetClassType + "Builder";
    String builderSimpleClassName = builderClassName.substring(lastDot + 1);

    JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

    try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
      final var targetClass = ClassName.get(targetClassType);

      final var classInstanceField =
          FieldSpec.builder(targetClass, "object", Modifier.PRIVATE, Modifier.FINAL)
              .initializer("new $T()", targetClass)
              .build();

      final var classBuildMethod =
          MethodSpec.methodBuilder("build")
              .addModifiers(Modifier.PUBLIC)
              .returns(targetClass)
              .addStatement("return $L", classInstanceField.name)
              .build();

      final var builderClass =
          TypeSpec.classBuilder(builderSimpleClassName)
              .addModifiers(Modifier.PUBLIC)
              .addField(classInstanceField)
              .addMethod(classBuildMethod);

      setterMethodsMap.forEach(
          (methodName, argumentType) ->
              builderClass.addMethod(
                  MethodSpec.methodBuilder(methodName)
                      .addModifiers(Modifier.PUBLIC)
                      .returns(ClassName.bestGuess(builderClass.build().name))
                      .addParameter(TypeName.get(argumentType), "value")
                      .addStatement("$L.$L(value)", classInstanceField.name, methodName)
                      .addStatement("return this")
                      .build()));

      JavaFile javaFile = JavaFile.builder(packageName, builderClass.build()).build();
      javaFile.writeTo(out);
    }
  }

  private boolean hasOneParameterAndStartWithSet(Element it) {
    return ((ExecutableType) it.asType()).getParameterTypes().size() == 1
        && it.getSimpleName().toString().startsWith("set");
  }

  private void printMessage(Diagnostic.Kind kind, String message, Element element) {
    processingEnv.getMessager().printMessage(kind, message, element);
  }
}
