package com.danzki.hw04.aop;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.security.ProtectionDomain;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class Agent {
  public static void premain(String[] agentArgs, Instrumentation inst) {
    inst.addTransformer(new ClassFileTransformer() {
      @Override
      public byte[] transform(ClassLoader loader,
                              String className,
                              Class<?> classBeingRedefined,
                              ProtectionDomain protectionDomain,
                              byte[] classfileBuffer) {
        if (className.equals("com/danzki/hw04/aop/Application")) {
          return addProxyMethod(classfileBuffer);
        }
        return classfileBuffer;
      }
    });
  }

  private static byte[] addProxyMethod(byte[] originalClass) {
    ClassReader cr = new ClassReader(originalClass);
    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
    ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
      @Override
      public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("printAppsList")) {
          return super.visitMethod(access, "printAppsListProxied", descriptor, signature, exceptions);
        } else {
          return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
      }

      @Override
      public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
      }
    };

    MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "printAppsList", "(Ljava/lang/String;)V", null, null);
    Handle h = new Handle(
        H_INVOKESTATIC,
        Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
        "makeConcatWithConstants",
        MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
        false);

    AnnotationVisitor av = mv.visitAnnotation("Log", true);

    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    mv.visitVarInsn(Opcodes.ALOAD, 1);
    mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(Ljava/lang/String;)Ljava/lang/String;", h, "logged param:\u0001");
    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

    mv.visitVarInsn(Opcodes.ALOAD, 0);
    mv.visitVarInsn(Opcodes.ALOAD, 1);
    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/danzki/hw04/aop/Application", "printAppsListProxied", "(Ljava/lang/String;)V", false);

    mv.visitInsn(Opcodes.RETURN);
    mv.visitMaxs(0, 0);
    mv.visitEnd();


    byte[] finalClass = cw.toByteArray();

    try (OutputStream fos = new FileOutputStream("proxyASM.class")) {
      fos.write(finalClass);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return finalClass;
  }

}















