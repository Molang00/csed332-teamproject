package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MIParseTest extends LightJavaCodeInsightFixtureTestCase {

    private PsiClass[] getPsiClasses(String path) {
        byte[] data = null;

        try {
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            data = new byte[(int) f.length()];
            if(fis.read(data) == -1) {
                fail(); // Unexpected EOF
            }
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String str = null;
        str = new String(data, StandardCharsets.UTF_8);

        PsiFile psiFile = myFixture.configureByText(JavaFileType.INSTANCE, str);
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
        return psiJavaFile.getClasses();
    }

    @Test
    public void testMISimple() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase1.txt");

        for (PsiClass psiClass : psiClasses) {
            List<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            System.out.println("test simple operand size = " + operands.size());
            for (int j = 0; j < operands.size(); j++) {
                System.out.println("operand " + j + " : " + operands.get(j).toString());
            }
            assertEquals(operands.size(), 4);

            List<PsiElement> operators = ParseAdapter.getOperators(psiClass);
            System.out.println("test simple operator size = " + operators.size());
            for (int j = 0; j < operators.size(); j++) {
                System.out.println("operator " + j + " : " + operators.get(j).toString());
            }
            assertEquals(operators.size(), 2);
        }
    }


    @Test
    public void testMIMany() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");
        for (PsiClass psiClass : psiClasses) {
            List<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            System.out.println("test many operand size = " + operands.size());
            for (int j = 0; j < operands.size(); j++) {
                Object obj = operands.get(j);
                System.out.println("operand " + j + " : " + obj.toString());
            }
            assertEquals(operands.size(), 41);

            List<PsiElement> operators = ParseAdapter.getOperators(psiClass);
            System.out.println("test many operator size = " + operators.size());
            for (int j = 0; j < operators.size(); j++) {
                System.out.println("operator " + j + " : " + operators.get(j).toString());
            }
            assertEquals(operators.size(), 21);
        }
    }

}