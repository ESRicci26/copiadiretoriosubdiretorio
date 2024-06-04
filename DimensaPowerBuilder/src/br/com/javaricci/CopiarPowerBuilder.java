package br.com.javaricci;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;


public final class CopiarPowerBuilder extends JFrame {
	private static final long serialVersionUID = 1L;

	JScrollPane scrollpane;

    private JLabel JLabel1Origem, JLabelDestino;
    private JTextField JTFOrigem, JTFDestino;
    private JButton JBTCopiarDiretorio, LimparCampos, FecharExit;

    public CopiarPowerBuilder() {
        super("Copiar Projeto PowerBuilder SLA TOTVS");
        setSize(500, 160); //Largura (600) Altura(200)
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        setVisible(true);

    }

    public void init() {

        JPanel NovoJPanel = new JPanel();
        NovoJPanel.setSize(600, 200); //Largura (600) Altura(200)
        NovoJPanel.setLayout(new GridLayout(5, 5, 1, 0));
        scrollpane = new JScrollPane(NovoJPanel); //Adiciona JPanel em JScrollPane
        getContentPane().add(scrollpane, BorderLayout.CENTER);

        JLabel1Origem = new JLabel("Informe Caminho ORIGEM");
        NovoJPanel.add(JLabel1Origem);
        JTFOrigem = new JTextField();
        NovoJPanel.add(JTFOrigem);
        
        JLabelDestino = new JLabel("Informe Caminho DESTINO");
        NovoJPanel.add(JLabelDestino);
        JTFDestino = new JTextField();
        NovoJPanel.add(JTFDestino);

        JBTCopiarDiretorio = new JButton("Copiar");
        NovoJPanel.add(JBTCopiarDiretorio);

        LimparCampos = new JButton("Limpar");
        NovoJPanel.add(LimparCampos);

        FecharExit = new JButton("Sair");
        NovoJPanel.add(FecharExit);

        
        JBTCopiarDiretorio.addActionListener(new ActionListener() { 
        	@Override  
        	public void actionPerformed(ActionEvent e) { 

        		//O caminho deve ser informado assim: E:\\FOPAGRH
                String sourcePathString = JTFOrigem.getText();
                //System.out.print("Informe o caminho de origem: "+sourcePathString);

              //O caminho deve ser informado assim: C:\\DBSQLite
                String targetPathString = JTFDestino.getText();
                //System.out.print("Informe o caminho de destino: "+targetPathString);

                Path sourceDir = Paths.get(sourcePathString);
                Path targetDir = Paths.get(targetPathString);

                try {
                    Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            Path targetPath = targetDir.resolve(sourceDir.relativize(dir));
                            if (!Files.exists(targetPath)) {
                                Files.createDirectory(targetPath);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.copy(file, targetDir.resolve(sourceDir.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    JOptionPane.showMessageDialog(null, "Cópia concluída com sucesso!!!");
                } catch (IOException f) {
                    f.printStackTrace();
                    System.err.println("Erro durante a cópia: " + f.getMessage());
                    JOptionPane.showMessageDialog(null, "Erro durante a cópia: " + f.getMessage());
                }
            }
        		
        	 // } 
        	});


        FecharExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             System.exit(EXIT_ON_CLOSE);
            }
        });

        
        LimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LimparCampos();
            }
        });

    }

    public void LimparCampos() {
    	JTFOrigem.setText("");
    	JTFDestino.setText("");
    }

    
    public static void main(String args[]) {
        CopiarPowerBuilder totvsSla = new CopiarPowerBuilder();
    }

    
}
