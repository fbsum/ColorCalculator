package com.fbsum.plugin.colorcalculator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil
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
        percentageBox.layout = BoxLayout(percentageBox, BoxLayout.X_AXIS)
        percentageBox.maximumSize = Dimension(java.lang.Short.MAX_VALUE.toInt(), 54)
        percentageBox.add(Box.createRigidArea(Dimension(1, 0)))
        percentageBox.add(percentageLabel)
        percentageBox.add(Box.createRigidArea(Dimension(1, 0)))
        percentageBox.add(percentageTextField)

        // alpha value
        val alphaLabel = JLabel("Alpha = ")
        alphaLabel.preferredSize = Dimension(100, 26)
        alphaTextField = JTextField(10)
        alphaTextField.preferredSize = Dimension(100, 26)
        val alphaBox = JPanel()
        alphaBox.layout = BoxLayout(alphaBox, BoxLayout.X_AXIS)
        alphaBox.maximumSize = Dimension(java.lang.Short.MAX_VALUE.toInt(), 54)
        alphaBox.add(Box.createRigidArea(Dimension(1, 0)))
        alphaBox.add(alphaLabel)
        alphaBox.add(Box.createRigidArea(Dimension(1, 0)))
        alphaBox.add(alphaTextField)

        // hex value
        val hexLabel = JLabel("Hex = ")
        hexLabel.preferredSize = Dimension(100, 26)
        hexTextField = JTextField(10)
        hexTextField.preferredSize = Dimension(100, 26)
        val hexBox = JPanel()
        hexBox.layout = BoxLayout(hexBox, BoxLayout.LINE_AXIS)
        hexBox.maximumSize = Dimension(java.lang.Short.MAX_VALUE.toInt(), 54)
        hexBox.add(Box.createRigidArea(Dimension(1, 0)))
        hexBox.add(hexLabel)
        hexBox.add(Box.createRigidArea(Dimension(1, 0)))
        hexBox.add(hexTextField)

        // confirm button
        val confirmButton = JButton()
        confirmButton.addActionListener({
            calculate()
        })
        confirmButton.preferredSize = Dimension(120, 26)
        confirmButton.text = "Calculate"
        confirmButton.isVisible = true

        // content panel
        val panel = JPanel()
        panel.preferredSize = Dimension(640, 360)
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
        panel.add(Box.createRigidArea(Dimension(0, 20)))
        panel.add(percentageBox)
        panel.add(Box.createRigidArea(Dimension(0, 20)))
        panel.add(alphaBox)
        panel.add(Box.createRigidArea(Dimension(0, 20)))
        panel.add(hexBox)
        panel.add(Box.createRigidArea(Dimension(0, 20)))
        panel.add(confirmButton)

        // frame
        frame = JFrame()
        frame.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        frame.contentPane.add(panel)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }

    private fun calculate() {
        var percentage: Int = if (StringUtil.isEmpty(percentageTextField.text)) {
            -1
        } else {
            percentageTextField.text.toInt()
        }
        var alpha: Int = if (percentage >= 0) {
            percentage * 255 / 100
        } else {
            if (StringUtil.isEmpty(alphaTextField.text)) {
                0
            } else {
                alphaTextField.text.toInt()
            }
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
