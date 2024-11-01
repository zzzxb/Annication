package com.github.zzzxb.annication;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

final class MyTypedHandler implements TypedActionHandler {
    @Override
    public void execute(@NotNull Editor editor,
                        char c,
                        @NotNull DataContext dataContext) {
//        Document document = editor.getDocument();
        Project project = editor.getProject();
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        Document document = primaryCaret.getEditor().getDocument();
        int start = primaryCaret.getVisualLineStart();
        int end = primaryCaret.getVisualLineEnd();
        String allTxt = document.getText();
        if(StringUtils.isBlank(allTxt) || allTxt.length() < end) return;

        String text = allTxt.substring(start, end);
        if(StringUtils.isBlank(text)) return;
        if(text.trim().startsWith("--")) {
            text = text.replaceFirst("--", "");
        }else {
            text = "--" + text;
        }

        String finalText = text;
        Runnable runnable = () -> document.replaceString(start, end, finalText);
        WriteCommandAction.runWriteCommandAction(project, runnable);
    }
}