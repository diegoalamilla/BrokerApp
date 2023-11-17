/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.arquitectura.brokerApp.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author miguel
 */
public class ViewVote extends javax.swing.JFrame {

    /**
     * Creates new form ViewVote
     */
    public ViewVote() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fieldNumberOfVotes = new javax.swing.JTextField();
        labelProducto = new javax.swing.JLabel();
        labelNumberOfVotes = new javax.swing.JLabel();
        labelTitle = new javax.swing.JLabel();
        buttonVote = new javax.swing.JButton();
        labelActualVotes = new javax.swing.JLabel();
        comboProducts = new javax.swing.JComboBox<>();
        buttonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 200));

        labelProducto.setText("Producto:");

        labelNumberOfVotes.setText("Número de votos:");

        labelTitle.setFont(new java.awt.Font("Cantarell Thin", 1, 18)); // NOI18N
        labelTitle.setText("Votar");

        buttonVote.setText("Votar!");

        comboProducts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonBack.setText("Regresar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(labelTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonVote)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelActualVotes))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelProducto)
                                    .addComponent(labelNumberOfVotes))
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldNumberOfVotes)
                                    .addComponent(comboProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBack)
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitle)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelProducto)
                    .addComponent(comboProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNumberOfVotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNumberOfVotes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonVote)
                    .addComponent(labelActualVotes)
                    .addComponent(buttonBack))
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewVote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewVote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewVote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewVote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewVote().setVisible(true);
            }
        });
    }

    public JButton getButtonVote() {
        return buttonVote;
    }

    public void setButtonVote(JButton buttonVote) {
        this.buttonVote = buttonVote;
    }

    public JTextField getFieldNumberOfVotes() {
        return fieldNumberOfVotes;
    }

    public void setFieldNumberOfVotes(JTextField fieldNumberOfVotes) {
        this.fieldNumberOfVotes = fieldNumberOfVotes;
    }

    public JButton getButtonBack() {
        return buttonBack;
    }

    public void setButtonBack(JButton buttonBack) {
        this.buttonBack = buttonBack;
    }

    public JComboBox<String> getComboProducts() {
        return comboProducts;
    }

    public void setComboProducts(JComboBox<String> comboProducts) {
        this.comboProducts = comboProducts;
    }


    

    public JLabel getLabelActualVotes() {
        return labelActualVotes;
    }

    public void setLabelActualVotes(JLabel labelActualVotes) {
        this.labelActualVotes = labelActualVotes;
    }

    public JLabel getLabelNumberOfVotes() {
        return labelNumberOfVotes;
    }

    public void setLabelNumberOfVotes(JLabel labelNumberOfVotes) {
        this.labelNumberOfVotes = labelNumberOfVotes;
    }

    public JLabel getLabelProducto() {
        return labelProducto;
    }

    public void setLabelProducto(JLabel labelProducto) {
        this.labelProducto = labelProducto;
    }

    public JLabel getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(JLabel labelTitle) {
        this.labelTitle = labelTitle;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBack;
    private javax.swing.JButton buttonVote;
    private javax.swing.JComboBox<String> comboProducts;
    private javax.swing.JTextField fieldNumberOfVotes;
    private javax.swing.JLabel labelActualVotes;
    private javax.swing.JLabel labelNumberOfVotes;
    private javax.swing.JLabel labelProducto;
    private javax.swing.JLabel labelTitle;
    // End of variables declaration//GEN-END:variables
}
