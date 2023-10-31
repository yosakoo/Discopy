package com.example.discopy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DiscopyAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (editor == null) {
            Messages.showMessageDialog("No editor found.", "Copy Action", Messages.getInformationIcon());
            return;
        }

        String selectedText = editor.getSelectionModel().getSelectedText();

        if (selectedText == null) {
            Messages.showMessageDialog("Selection is empty, please select something.", "Copy Action", Messages.getInformationIcon());
            return;
        }

        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(editor.getDocument());

        if (virtualFile == null) {
            Messages.showMessageDialog("The selected text is not associated with a file.", "Copy Action", Messages.getInformationIcon());
            return;
        }

        String fileExtension = virtualFile.getExtension();

        if (fileExtension == null) {
            Messages.showMessageDialog("Unable to determine file extension.", "Copy Action", Messages.getInformationIcon());
            return;
        }

        String textToCopy = "```" + fileExtension + "\n" + selectedText + "\n```";
        StringSelection stringSelection = new StringSelection(textToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}