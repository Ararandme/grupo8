package eventos;

import arreglos.ArregloAlumno;
import arreglos.ArregloCurso;
import arreglos.ArregloMatricula;
import arreglos.ArregloRetiro;
import guis.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class MenuPrincipalEvent implements ComponentsSetters {
    HashMap<String, JComponent> componentes;
    JFrame frame;

    private final ConsultaAlumnosCursoEvent conCursoEvent;
    private final ConsultaMatriculasRetirosEvent conRetiroEvent;
    private final MantenimientoAlumnoEvent mantenimientoAlumnoEvent;
    private final MantenimientoCursoEvent mantenimientoCursoEvent;
    private final RegistroMatriculaEvent registroMatriculaEvent;

    private final ArregloAlumno arregloAlumno;
    private final ArregloCurso arregloCurso;
    private final ArregloRetiro arregloRetiro;
    private final ArregloMatricula arregloMatricul;
    private final RegistroRetiroEvent registroRetiroEvent;
    private final ReporteAlumnnosPendienteEvent reporteAlumnnosPendienteEvent;
    private final ReporteAlummnosVigentesEvent reporteAlummnosVigentesEvent;
    private final ReporteMatriculasCursoEvent reporteMatriculasCursoEvent;

    public MenuPrincipalEvent(ConsultaAlumnosCursoEvent consultaAlumnoEvent,
                              ConsultaMatriculasRetirosEvent conRetiroEvent,
                              MantenimientoAlumnoEvent mantenimientoAlumnoEvent,
                              MantenimientoCursoEvent mantenimientoCursoEvent,
                              RegistroMatriculaEvent registroMatriculaEvent,
                              RegistroRetiroEvent registroRetiroEvent,
                              ReporteAlumnnosPendienteEvent reporteAlumnnosPendienteEvent,
                              ReporteAlummnosVigentesEvent reporteAlummnosVigentesEvent,
                              ReporteMatriculasCursoEvent reporteMatriculasCursoEvent,
                              ArregloAlumno arregloAlumno,
                              ArregloCurso arregloCurso,
                              ArregloRetiro arregloRetiro,
                              ArregloMatricula arregloMatricula) {
        this.reporteAlummnosVigentesEvent = reporteAlummnosVigentesEvent;
        this.conCursoEvent = consultaAlumnoEvent;
        this.conRetiroEvent = conRetiroEvent;
        this.mantenimientoAlumnoEvent = mantenimientoAlumnoEvent;
        this.mantenimientoCursoEvent = mantenimientoCursoEvent;
        this.registroMatriculaEvent = registroMatriculaEvent;
        this.registroRetiroEvent = registroRetiroEvent;
        this.reporteAlumnnosPendienteEvent = reporteAlumnnosPendienteEvent;
        this.reporteMatriculasCursoEvent = reporteMatriculasCursoEvent;

        this.arregloAlumno = arregloAlumno;
        this.arregloCurso = arregloCurso;
        this.arregloRetiro = arregloRetiro;
        this.arregloMatricul = arregloMatricula;
    }

    public void setMnuSalirEvent(ActionEvent e){
        frame.dispose();
        new Login(null).setVisible(true);
    }
    public void setMntmMantenimientoAlumno (ActionEvent e){

        MantenimientoAlumno ma = new MantenimientoAlumno(mantenimientoAlumnoEvent,arregloAlumno);
        ma.setLocationRelativeTo(frame);
        ma.setVisible(true);
    }

    public void setMntmMantenimientoCurso (ActionEvent e){
        MantenimientoCurso mc = new MantenimientoCurso(mantenimientoCursoEvent,arregloCurso,arregloMatricul);
        mc.setLocationRelativeTo(frame);
        mc.setVisible(true);
    }
    public void setMntmRegistroMatricula (ActionEvent e){
        RegistroMatricula rm = new RegistroMatricula(registroMatriculaEvent,arregloMatricul);
        rm.setLocationRelativeTo(frame);
        rm.setVisible(true);
    }
    public void setMntmRegistroRetiro (ActionEvent e){
        RegistroRetiro rr = new RegistroRetiro(registroRetiroEvent,arregloRetiro);
        rr.setLocationRelativeTo(frame);
    }
    public void setMntmConsultaAlumnosCursos (ActionEvent e){
        ConsultaAlumnosCursos ca = new ConsultaAlumnosCursos(conCursoEvent);
        ca.setLocationRelativeTo(frame);
        ca.setVisible(true);
    }
    public void setMntmConsultaMatriculasRetiros (ActionEvent e){
        ConsultaMatriculasRetiros cm = new ConsultaMatriculasRetiros(conRetiroEvent);
        cm.setLocationRelativeTo(frame);
        cm.setVisible(true);
    }
    public void setMntmReporteMatriculaP (ActionEvent e){
        ReporteAlumnosPendientes rap = new ReporteAlumnosPendientes(reporteAlumnnosPendienteEvent);
        rap.setLocationRelativeTo(frame);
        rap.setVisible(true);
    }
    public void setMntmReporteMatriculaV (ActionEvent e){
        ReporteAlumnosVigentes rav = new ReporteAlumnosVigentes(reporteAlummnosVigentesEvent);
        rav.setLocationRelativeTo(frame);
        rav.setVisible(true);
    }
    public void setMntmReporteMatriculaC (ActionEvent e){
        ReporteMatriculasCurso rmc = new ReporteMatriculasCurso(reporteMatriculasCursoEvent);
        rmc.setLocationRelativeTo(frame);
        rmc.setVisible(true);
    }


    @Override
    public void setComponent(HashMap<String, JComponent> componentsMap) {
        this.componentes = componentsMap;
    }

    public void setFrame(JFrame frame){
        this.frame = frame;
    }
}
