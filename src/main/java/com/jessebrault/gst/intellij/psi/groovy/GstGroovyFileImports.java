package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFileBase;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.resolve.imports.*;

import java.util.Collection;
import java.util.List;

public class GstGroovyFileImports implements GroovyFileImports {

    private final GroovyFileBase file;

    public GstGroovyFileImports(GroovyFileBase file) {
        this.file = file;
    }

    @NotNull
    @Override
    public Collection<GroovyNamedImport> getAllNamedImports() {
        return List.of();
    }

    @NotNull
    @Override
    public GroovyFileBase getFile() {
        return this.file;
    }

    @NotNull
    @Override
    public Collection<StarImport> getStarImports() {
        return List.of();
    }

    @NotNull
    @Override
    public Collection<StaticStarImport> getStaticStarImports() {
        return List.of();
    }

    @NotNull
    @Override
    public Collection<GrImportStatement> findUnnecessaryStatements() {
        return List.of();
    }

    @NotNull
    @Override
    public Collection<GrImportStatement> findUnresolvedStatements(@NotNull Collection<String> collection) {
        return List.of();
    }

    @NotNull
    @Override
    public Collection<GroovyNamedImport> getImportsByName(@NotNull String s) {
        return List.of();
    }

    @Override
    public boolean isImplicit(@NotNull GroovyImport groovyImport) {
        return true;
    }

    @Override
    public boolean processAllNamedImports(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @NotNull PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean processAllStarImports(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @NotNull PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean processDefaultImports(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @NotNull PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean processStaticImports(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @NotNull PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean processStaticStarImports(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @NotNull PsiElement psiElement) {
        return false;
    }

}
