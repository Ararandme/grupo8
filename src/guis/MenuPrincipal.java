package guis;

import eventos.MenuPrincipalEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class MenuPrincipal extends JFrame implements ComponentEvents {

    private JPanel contentPane;
    private JMenuItem mntmSalir;
    private JMenuItem mntmMantenimientoAlumno;
    private JMenuItem mntmMantenimientoCurso;
    private JMenuItem mntmRegistroMatricula;
    private JMenuItem mntmRegistroRetiro;
    private JMenuItem mntmConsultaAlumnosCursos;
    private JMenuItem mntmConsultaMatriculasRetiros;
    private JMenu mnReporte;
    private JMenuItem mntmReporteMatriculaP;
    private JMenuItem mntmReporteMatriculaV;
    private JMenuItem mntmReporteMatriculaC;



    private String userType;
    private final MenuPrincipalEvent menuPrincipal;

    public MenuPrincipal(MenuPrincipalEvent menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        if (userType!=null)setAdminValidation();
        setSetting();
    }

    private void setAdminValidation(){

        if (!userType.equalsIgnoreCase("admin")) {
            mntmMantenimientoAlumno.setEnabled(false);
        }

    }

    private void setSetting(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 500);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnArchivo = new JMenu("Archivo");
        menuBar.add(mnArchivo);

        mntmSalir = new JMenuItem("Salir");
        mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        mnArchivo.add(mntmSalir);


        JMenu mnMantenimiento = new JMenu("Mantenimiento");
        menuBar.add(mnMantenimiento);

        mntmMantenimientoAlumno = new JMenuItem("Mantenimiento | Alumno");
        mntmMantenimientoAlumno.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
        mnMantenimiento.add(mntmMantenimientoAlumno);

        mntmMantenimientoCurso = new JMenuItem("Mantenimiento | Curso");
        mntmMantenimientoCurso.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        mnMantenimiento.add(mntmMantenimientoCurso);

        JMenu mnRegistro = new JMenu("Registro");
        menuBar.add(mnRegistro);

        mntmRegistroMatricula = new JMenuItem("Registro | Matricula");
        mntmRegistroMatricula.setAccelerator(KeyStroke.getKeyStroke("ctrl M"));
        mnRegistro.add(mntmRegistroMatricula);

        mntmRegistroRetiro = new JMenuItem("Registro | Retiro");
        mntmRegistroRetiro.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        mnRegistro.add(mntmRegistroRetiro);

        JMenu mnConsulta = new JMenu("Consulta");
        menuBar.add(mnConsulta);

        mntmConsultaAlumnosCursos = new JMenuItem("Consulta | Alumnos y Cursos");
        mntmConsultaAlumnosCursos.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        mnConsulta.add(mntmConsultaAlumnosCursos);

        mntmConsultaMatriculasRetiros = new JMenuItem("Consulta | Matrículas y Retiros");
        mntmConsultaMatriculasRetiros.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
        mnConsulta.add(mntmConsultaMatriculasRetiros);

        mnReporte = new JMenu("Reporte");
        menuBar.add(mnReporte);

        mntmReporteMatriculaP = new JMenuItem("Reporte | Alumnos con matrícula pendiente");
        mntmReporteMatriculaP.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        mnReporte.add(mntmReporteMatriculaP);

        mntmReporteMatriculaV = new JMenuItem("Reporte | Alumnos con matrícula vigente");
        mntmReporteMatriculaV.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        mnReporte.add(mntmReporteMatriculaV);

        mntmReporteMatriculaC = new JMenuItem("Reporte | Alumnos matriculados por curso");
        mntmReporteMatriculaC.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
        mnReporte.add(mntmReporteMatriculaC);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        sendComponents();
        sendingEvents();

    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    @Override
    public void sendingEvents() {
        mntmSalir.addActionListener((e)->{menuPrincipal.setMnuSalirEvent(e);});
        mntmMantenimientoAlumno.addActionListener((e)->{menuPrincipal.setMntmMantenimientoAlumno(e);});
        mntmMantenimientoCurso.addActionListener((e)->{menuPrincipal.setMntmMantenimientoCurso(e);});
        mntmRegistroMatricula.addActionListener((e)->{menuPrincipal.setMntmRegistroMatricula(e);});
        mntmRegistroRetiro.addActionListener((e)->{menuPrincipal.setMntmRegistroRetiro(e);});
        mntmConsultaAlumnosCursos.addActionListener((e)->{menuPrincipal.setMntmConsultaAlumnosCursos(e);});
        mntmConsultaMatriculasRetiros.addActionListener((e)->{menuPrincipal.setMntmConsultaMatriculasRetiros(e);});
        mntmReporteMatriculaP.addActionListener((e)->{menuPrincipal.setMntmReporteMatriculaP(e);});
        mntmReporteMatriculaV.addActionListener((e)->{menuPrincipal.setMntmReporteMatriculaV(e);});
        mntmReporteMatriculaC.addActionListener((e)->{menuPrincipal.setMntmReporteMatriculaC(e);});

    }

    @Override
    public void sendComponents() {
        HashMap<String, JComponent> map = new HashMap<>();
        menuPrincipal.setComponent(map);
        menuPrincipal.setFrame(this);
    }


}
