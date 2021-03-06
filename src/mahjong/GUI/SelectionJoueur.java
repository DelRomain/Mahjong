package mahjong.GUI;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import mahjong.GUI.utils.JTableModeleSelectionJoueur;
import mahjong.GestionJoueur.Joueur;

/**
 * Fenetre permettant de choisir quel joueur va jouer au jeu
 */
public class SelectionJoueur extends javax.swing.JPanel {

    private final Fenetre fenetre;

    public SelectionJoueur(Fenetre fenetre) {
        super();
        initComponents();
        this.fenetre = fenetre;
        rechargerListeJoueur();

        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point point = mouseEvent.getPoint();
                int row = jTable2.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    Joueur joueur = ((JTableModeleSelectionJoueur) jTable2.getModel()).getListe().get(row);
                    ListePartieJouee GUIListeDePartie = new ListePartieJouee(fenetre, true, joueur);
                    GUIListeDePartie.setVisible(true);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        nomJoueurActuel = new javax.swing.JLabel();
        jLabelNomJoueur = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonSelectionnerJoueur = new javax.swing.JButton();
        jButtonRetirerJoueur = new javax.swing.JButton();
        jButtonCreerJoueur = new javax.swing.JButton();
        jButtonRetourMenu = new javax.swing.JButton();
        jButtonRechercheJoueurDossier = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new JTableModeleSelectionJoueur());
        jTable2.setCellSelectionEnabled(true);
        jScrollPane3.setViewportView(jTable2);

        add(jScrollPane3, java.awt.BorderLayout.CENTER);

        nomJoueurActuel.setText("jLabel2");

        jLabelNomJoueur.setText("Joueur actuel :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabelNomJoueur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomJoueurActuel)
                .addGap(0, 340, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNomJoueur)
                    .addComponent(nomJoueurActuel))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jButtonSelectionnerJoueur.setText("Selectionner Joueur");
        jButtonSelectionnerJoueur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectionnerJoueurActionPerformed(evt);
            }
        });

        jButtonRetirerJoueur.setText("Retirer joueur");
        jButtonRetirerJoueur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetirerJoueurActionPerformed(evt);
            }
        });

        jButtonCreerJoueur.setText("Créer Joueur");
        jButtonCreerJoueur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreerJoueurActionPerformed(evt);
            }
        });

        jButtonRetourMenu.setText("Retour");
        jButtonRetourMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetourMenuActionPerformed(evt);
            }
        });

        jButtonRechercheJoueurDossier.setText("Actualiser");
        jButtonRechercheJoueurDossier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercheJoueurDossierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRetourMenu)
                    .addComponent(jButtonRechercheJoueurDossier))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonRetirerJoueur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSelectionnerJoueur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonCreerJoueur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelectionnerJoueur)
                    .addComponent(jButtonRechercheJoueurDossier))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRetirerJoueur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCreerJoueur)
                    .addComponent(jButtonRetourMenu))
                .addGap(5, 5, 5))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Permet de revenir au menu principale
     *
     * @param evt
     */
    private void jButtonRetourMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetourMenuActionPerformed
        fenetre.getGestionnaireJoueurs().sauvegarderJoueurs();
        fenetre.afficherMenuPrincipal();
    }//GEN-LAST:event_jButtonRetourMenuActionPerformed

    /**
     * Permet de sélectionner un joueur
     *
     * @param evt
     */
    private void jButtonSelectionnerJoueurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectionnerJoueurActionPerformed
        int index = jTable2.getSelectedRow();
        if (index != -1) {
            Joueur joueur = ((JTableModeleSelectionJoueur) jTable2.getModel()).getListe().get(index);
            fenetre.getGestionnaireJoueurs().setJoueur(joueur);
            nomJoueurActuel.setText(fenetre.getGestionnaireJoueurs().getJoueur().getNom());
        }
    }//GEN-LAST:event_jButtonSelectionnerJoueurActionPerformed

    /**
     * Permet de supprimer un joueur
     *
     * @param evt
     */
    private void jButtonRetirerJoueurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetirerJoueurActionPerformed
        int index = jTable2.getSelectedRow();
        if (index != -1) {
            Joueur joueur = ((JTableModeleSelectionJoueur) jTable2.getModel()).getListe().get(index);
            fenetre.getGestionnaireJoueurs().supprimerJoueur(joueur);
            ((JTableModeleSelectionJoueur) jTable2.getModel()).retirerEntree(index);
        }
        rechargerListeJoueur();
    }//GEN-LAST:event_jButtonRetirerJoueurActionPerformed

    /**
     * Permet d'ajouter un joueur
     *
     * @param evt
     */
    private void jButtonCreerJoueurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreerJoueurActionPerformed
        String nom = JOptionPane.showInputDialog(this, "Nom du nouveau joueur :", "Ajout joueur", JOptionPane.QUESTION_MESSAGE);
        if (!nom.equals("")) {
            Joueur joueur = new Joueur(nom);
            if (fenetre.getGestionnaireJoueurs().add(joueur)) {
                ((JTableModeleSelectionJoueur) jTable2.getModel()).ajouterEntree(joueur);
                rechargerListeJoueur();
            } else {
                JOptionPane.showMessageDialog(this, "Ce nom est déjà pris!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonCreerJoueurActionPerformed

    /**
     * Permet d'actualiser la liste de joueur avec les joueurs présents dans le
     * dossier joueur du repoertoire du jeu
     *
     * @param evt
     */
    private void jButtonRechercheJoueurDossierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercheJoueurDossierActionPerformed
        fenetre.getGestionnaireJoueurs().chargerJoueurs();
    }//GEN-LAST:event_jButtonRechercheJoueurDossierActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreerJoueur;
    private javax.swing.JButton jButtonRechercheJoueurDossier;
    private javax.swing.JButton jButtonRetirerJoueur;
    private javax.swing.JButton jButtonRetourMenu;
    private javax.swing.JButton jButtonSelectionnerJoueur;
    private javax.swing.JLabel jLabelNomJoueur;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel nomJoueurActuel;
    // End of variables declaration//GEN-END:variables

    /**
     * Met à jour la liste des joueurs à afficher
     */
    public final void rechargerListeJoueur() {
        for (int i = jTable2.getModel().getRowCount(); i > 0; i--) {
            ((JTableModeleSelectionJoueur) jTable2.getModel()).retirerEntree(0);
        }
        if (fenetre.getGestionnaireJoueurs().getJoueur() != null) {
            nomJoueurActuel.setText(fenetre.getGestionnaireJoueurs().getJoueur().getNom());
        } else {
            nomJoueurActuel.setText("");
        }
        fenetre.getGestionnaireJoueurs().getListeJoueurs().forEach((j) -> {
            ((JTableModeleSelectionJoueur) jTable2.getModel()).ajouterEntree(j);
        });
    }
}
