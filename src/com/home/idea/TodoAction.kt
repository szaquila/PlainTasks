package com.home.idea

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.util.PsiUtilBase


abstract class TodoAction : AnAction() {
    val LOG = Logger.getInstance("PlainTasks")

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
//        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val editor = e.getData(PlatformDataKeys.EDITOR) ?: return
        val currentFile = PsiUtilBase.getPsiFileInEditor(editor, project)
        if (currentFile != null && currentFile.name != "TODO" && currentFile.name != "todolist.txt") {
            return
        }

        var initialValue = ""

        val selectionModel = editor.selectionModel

        if (!selectionModel.hasSelection()) {
            selectionModel.selectLineAtCaret()
        }

        var selectedText = selectionModel.selectedText
        if (selectedText != null && !selectedText.isEmpty()) {
             selectedText = selectedText.trimEnd { it <= ' ' }

            if (selectedText.contains("\n")) {
                selectedText = selectedText.substring(0, selectedText.indexOf("\n"))
            }

            initialValue = selectedText
        }

        val text = parseSelectedText(initialValue)
        val runnable = Runnable {
            if (text == null) return@Runnable
            var offset = editor.caretModel.offset
            val document = editor.document

            // Check if we should replace the selected text
            if (selectionModel.hasSelection()) {
                val selectionStart = selectionModel.selectionStart
                val selectionEnd = selectionModel.selectionEnd

                selectionModel.removeSelection()
                document.deleteString(selectionStart, selectionEnd)
                offset = selectionStart
            }

            // val lineNumber = document.getLineNumber(offset)
            // val lineStartOffset = document.getLineStartOffset(lineNumber)
            // val indentLength = offset - lineStartOffset

            document.insertString(offset, text + "\n")
            selectionModel.setSelection(offset, offset)
        }

        WriteCommandAction.runWriteCommandAction(e.getData(PlatformDataKeys.PROJECT), runnable)
        // ApplicationManager.getApplication().invokeLater { CommandProcessor.getInstance().executeCommand(project, { ApplicationManager.getApplication().runWriteAction(runnable) }, "PlainTasks", null) }
    }

    abstract fun parseSelectedText(initialValue: String): String?
}
