package com.fbsum.plugin.colorcalculator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import java.awt.Dimension
import javax.swing.*

class ColorCalculatorAction : AnAction() {

    private val log = Logger.getInstance(ColorCalculatorAction::class.java)

    lateinit var frame: JFrame
    lateinit var percentageTextField: JTextField
    lateinit var alphaTextField: JTextField
    lateinit var hexTextField: JTextField

    override fun actionPerformed(e: AnActionEvent) {
        showFrame()
    }

    private fun showFrame() {
        // percentage value
        val percentageLabel = JLabel("Percentage = ")
        percentageLabel.preferredSize = Dimension(100, 26)
        percentageTextField = JTextField(10)
        percentageTextField.preferredSize = Dimension(100, 26)
        val percentageBox = JPanel()
        with(percentageBox) {
            layout = BoxLayout(percentageBox, BoxLayout.X_AXIS)
            maximumSize = Dimension(java.lang.Short.MAX_VALUE.toInt(), 54)
            add(Box.createRigidArea(Dimension(1, 0)))
            add(percentageLabel)
            add(Box.createRigidArea(Dimension(1, 0)))
            add(percentageTextField)
        }

        // alpha value
        val alphaLabel = JLabel("Alpha = ")
        alphaLabel.preferredSize = Dimension(100, 26)
        alphaTextField = JTextField(10)
        alphaTextField.preferredSize = Dimension(100, 26)
        val alphaBox = JPanel()
        with(alphaBox) {
            layout = BoxLayout(alphaBox, BoxLayout.X_AXIS)
            maximumSize = Dimension(java.lang.Short.MAX_VALUE.toInt(), 54)
            add(Box.createRigidArea(Dimension(1, 0)))
            add(alphaLabel)
            add(Box.createRigidArea(Dimension(1, 0)))
            add(alphaTextField)
        }

        // hex value
        val hexLabel = JLabel("Hex = ")
        hexLabel.preferredSize = Dimension(100, 26)
        hexTextField = JTextField(10)
        hexTextField.preferredSize = Dimension(100, 26)
        val hexBox = JPanel()
        with(hexBox) {
            layout = BoxLayout(hexBox, BoxLayout.LINE_AXIS)
            maximumSize = Dimension(java.lang.Short.MAX_VALUE.toInt(), 54)
            add(Box.createRigidArea(Dimension(1, 0)))
            add(hexLabel)
            add(Box.createRigidArea(Dimension(1, 0)))
            add(hexTextField)
        }

        // confirm button
        val confirmButton = JButton()
        with(confirmButton) {
            addActionListener({ calculate() })
            preferredSize = Dimension(120, 26)
            text = "Calculate"
            isVisible = true
        }

        // content panel
        val panel = JPanel()
        with(panel) {
            preferredSize = Dimension(640, 360)
            layout = BoxLayout(panel, BoxLayout.Y_AXIS)
            border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
            add(Box.createRigidArea(Dimension(0, 20)))
            add(percentageBox)
            add(Box.createRigidArea(Dimension(0, 20)))
            add(alphaBox)
            add(Box.createRigidArea(Dimension(0, 20)))
            add(hexBox)
            add(Box.createRigidArea(Dimension(0, 20)))
            add(confirmButton)
        }

        // frame
        frame = JFrame()
        with(frame) {
            defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
            contentPane.add(panel)
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }

    private fun calculate() {
        var percentage = percentageTextField.text?.toInt() ?: -1
        var alpha: Int = if (percentage >= 0) {
            percentage * 255 / 100
        } else {
            alphaTextField.text?.toInt() ?: 0
        }
        alpha = if (alpha < 0) 0 else alpha
        alpha = if (alpha > 255) 255 else alpha
        percentage = (alpha * 100 + 254) / 255
        val hex = String.format("%02X", 0xFF and alpha)

        percentageTextField.text = percentage.toString()
        alphaTextField.text = alpha.toString()
        hexTextField.text = hex
    }
}
