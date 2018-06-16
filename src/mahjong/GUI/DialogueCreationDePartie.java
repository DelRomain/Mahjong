package mahjong.GUI;

import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import mahjong.Type_Plateau.TypePlateau;
import mahjong.partie.Chrono;
import mahjong.partie.SauvegardePartie;

/**
 * Gère la fenetre permettant de créer ou charger une partie
 */
public class DialogueCreationDePartie extends javax.swing.JDialog {

    private final Fenetre fenetre;

    /**
     * Crée une nouvelle fenetre de parametrage de partie
     *
     * @param parent
     * @param modal
     */
    public DialogueCreationDePartie(Fenetre parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Création d'une partie");
        initComponents();
        this.fenetre = parent;
        this.labelNomJoeur.setText(fenetre.getGestionnaireJoueurs().getJoueur().getNom());
        this.comboBoxTypeTerrain.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boutonLancer = new javax.swing.JButton();
        boutonAnnuler = new javax.swing.JButton();
        boutonCharger = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelNomJoeur = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboBoxTypeTerrain = new javax.swing.JComboBox<>();
        textFieldSeed = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        boutonLancer.setText("Lancer");
        boutonLancer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonLancerActionPerformed(evt);
            }
        });

        boutonAnnuler.setText("Annuler");
        boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonAnnulerActionPerformed(evt);
            }
        });

        boutonCharger.setText("Charger");
        boutonCharger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonChargerActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Elephant", 1, 24)); // NOI18N
        jLabel1.setText("Création d'une nouvelle partie");

        jLabel2.setText("Joueur :");

        labelNomJoeur.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        labelNomJoeur.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelNomJoeur)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelNomJoeur))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Partie")));

        jLabel4.setLabelFor(textFieldSeed);
        jLabel4.setText("Seed :");

        jLabel5.setText("Type de terrain :");

        comboBoxTypeTerrain.setModel(new javax.swing.DefaultComboBoxModel((genererListeComboBox())));

        textFieldSeed.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxTypeTerrain, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSeed)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addComponent(textFieldSeed, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxTypeTerrain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(boutonAnnuler)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonCharger)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonLancer)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutonLancer)
                    .addComponent(boutonAnnuler)
                    .addComponent(boutonCharger))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Permet de revenir au menu principale
     * @param evt 
     */
    private void boutonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonAnnulerActionPerformed
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_boutonAnnulerActionPerformed

    /**
     * Lance une nouvelle partie
     * @param evt 
     */
    private void boutonLancerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonLancerActionPerformed
        long seed;
        if (textFieldSeed.getValue() != null) {
            seed = (long) textFieldSeed.getValue();
        } else {
            Random random = new Random();
            seed = random.nextLong();
            System.out.println("generation seed : " + seed);
        }
        fenetre.lancerPartie(seed, TypePlateau.getTypeParNom((String) comboBoxTypeTerrain.getSelectedItem()));
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_boutonLancerActionPerformed

    /**
     * Affiche la fenetre de chargement d'une partie sauvegardé
     * @param evt 
     */
    private void boutonChargerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonChargerActionPerformed
        JFileChooser fileChooser = new JFileChooser("partie");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                boolean result = false;
                if (f.isFile() && f.getName().endsWith(".mprt")) {
                    result = true;
                }
                return result;
            }

            @Override
            public String getDescription() {
                return "Fichier de partie mahjong";
            }
        });
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            SauvegardePartie partieChargee = new SauvegardePartie();
            partieChargee.charger(file.getAbsolutePath());
            
            int chargerPartie = JOptionPane.showConfirmDialog(
                    this,
                    "Voulez-vous charger cette partie :\n"
                    + "Joueur : " + partieChargee.getJoueur().getNom() + "\n"
                    + "Type de plateau : " + partieChargee.getPartie().getPlateau().getTypeDePlateau().getNom() + "\n"
                    + "Temps : " + Chrono.getTempsFormate(partieChargee.getPartie().getTempsDejeu()) + "\n"
                    + "Score : " + partieChargee.getPartie().getScore(),
                    "Chargement partie",
                    JOptionPane.YES_NO_OPTION);
            
            if (chargerPartie == JOptionPane.OK_OPTION) {
                fenetre.lancerPartie(partieChargee);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }
    }//GEN-LAST:event_boutonChargerActionPerformed
    
    /**
     * Gènere la liste des types de plateaux disponible pour l'affichage dans la comboBox
     * @return la liste des noms de type de plateau
     */
    private String[] genererListeComboBox() {
        String[] listeItems = new String[TypePlateau.values().length];
        for (int i = 0; i < TypePlateau.values().length; i++) {
            listeItems[i] = TypePlateau.values()[i].getNom();
        }
        return listeItems;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonAnnuler;
    private javax.swing.JButton boutonCharger;
    private javax.swing.JButton boutonLancer;
    private javax.swing.JComboBox<String> comboBoxTypeTerrain;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelNomJoeur;
    private javax.swing.JFormattedTextField textFieldSeed;
    // End of variables declaration//GEN-END:variables
}
