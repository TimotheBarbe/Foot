package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import model.Obs;
import controle.ControleBoutonExportCarte;
import controle.ControleBoutonExportExcel;
import controle.ControleBoutonImportExcel;
import controle.ControleImage;
import controle.ControleJBoxToutCocher;
import controle.ControleJColumnClub;
import controle.ControleRecherche;

public class MainWindows extends JFrame {

	private static final long serialVersionUID = 1L;
	private static int LABEL_SIZE = 12;
	private Obs obs;

	public static String pathCarte = "Donnees/carte_region.jpg";
	public static String pathLogo = "Donnees/Logo_DistrictFootball44.jpg";

	public MainWindows(Obs obs, String nomDivision) {
		this.setPreferredSize(new Dimension(800, 600));
		this.setTitle(nomDivision);
		this.obs = obs;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.setVisible(true);

		this.creerCarte();
		this.creerGauche();
		this.creerBas();
		this.pack();
	}

	private void creerGauche() {
		JPanel gauche = new JPanel(new BorderLayout());

		// table
		TableClub model = new TableClub(obs);
		TableRowSorter<TableClub> sorter = new TableRowSorter<TableClub>(model);
		sorter.setSortable(0, false);
		sorter.setSortable(1, false);

		JTable table = new JTable(model) {
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				if (rowIndex >= 0 && colIndex >= 0) {
					int realColumnIndex = convertColumnIndexToModel(colIndex);

					if (realColumnIndex == 1) {
						tip = "" + getValueAt(rowIndex, colIndex);
					} else {
						tip = super.getToolTipText(e);
					}
				}
				return tip;
			}
		};
		table.setRowSorter(sorter);
		table.setPreferredScrollableViewportSize(new Dimension(200, 600));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(145);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);

		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		ControleJColumnClub controleTable = new ControleJColumnClub(obs, table,
				model);
		cellSelectionModel.addListSelectionListener(controleTable);
		table.addMouseListener(controleTable);
		this.obs.addObserver(controleTable);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		gauche.add(scrollPane, BorderLayout.CENTER);

		// text field recherche
		JPanel panelRecherche = new JPanel(new BorderLayout());
		JLabel labelRecherche = new JLabel("Rechercher :");
		panelRecherche.add(labelRecherche, BorderLayout.NORTH);
		JTextField filterText = new JTextField();
		filterText.getDocument().addDocumentListener(
				new ControleRecherche(sorter, filterText));
		labelRecherche.setLabelFor(filterText);
		panelRecherche.add(filterText, BorderLayout.CENTER);
		panelRecherche.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		gauche.add(panelRecherche, BorderLayout.NORTH);

		// TOUT COCHER
		JPanel panelTickBox = new JPanel(new BorderLayout());
		// tickbox
		JCheckBox tickToutCocher = new JCheckBox();
		panelTickBox.add(tickToutCocher, BorderLayout.WEST);
		ControleJBoxToutCocher controleBox = new ControleJBoxToutCocher(
				this.obs);
		tickToutCocher.addActionListener(controleBox);
		// titre
		JLabel labelToutCocher = new JLabel("Tout cocher");
		labelToutCocher.setFont(new Font(labelToutCocher.getFont().getName(),
				Font.BOLD, LABEL_SIZE));
		panelTickBox.add(labelToutCocher, BorderLayout.CENTER);
		gauche.add(panelTickBox, BorderLayout.SOUTH);

		this.getContentPane().add(gauche, BorderLayout.WEST);
	}

	private void creerCarte() {
		JPanel panelCarte = new JPanel(new BorderLayout());

		panelCarte.setPreferredSize(new Dimension(800, 600));
		panelCarte.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		AfficheImage affIm = new AfficheImage(obs);
		panelCarte.add(affIm, BorderLayout.CENTER);
		ControleImage cimg = new ControleImage(affIm);

		this.obs.addObserver(cimg);
		this.getContentPane().add(panelCarte, BorderLayout.CENTER);
	}

	private void creerBas() {
		JPanel panelBas = new JPanel(new BorderLayout());

		JPanel panelWest = new JPanel(new BorderLayout());
		panelWest.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// charger solution
		JButton importsolution = new JButton("Importer une solution",
				new ImageIcon("Donnees/icone_excel.png"));
		importsolution.addActionListener(new ControleBoutonImportExcel(this));
		panelWest.add(importsolution, BorderLayout.WEST);

		// BOUTONS
		JPanel panelEast = new JPanel(new BorderLayout());
		panelEast.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// export solution
		JButton exportsolution = new JButton("Exporter la solution",
				new ImageIcon("Donnees/icone_excel.png"));
		exportsolution.addActionListener(new ControleBoutonExportExcel(obs));
		panelEast.add(exportsolution, BorderLayout.WEST);
		// espace vide
		panelEast.add(Box.createRigidArea(new Dimension(5, 0)),
				BorderLayout.CENTER);
		// export carte
		JButton exportCarte = new JButton("Exporter les cartes", new ImageIcon(
				"Donnees/logo-pdf.png"));
		exportCarte.addActionListener(new ControleBoutonExportCarte(obs));
		panelEast.add(exportCarte, BorderLayout.EAST);

		panelBas.add(panelWest, BorderLayout.WEST);
		panelBas.add(panelEast, BorderLayout.EAST);
		this.getContentPane().add(panelBas, BorderLayout.SOUTH);
	}
}
