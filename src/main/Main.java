package main;

import arreglos.*;
import eventos.*;
import guis.*;


public class Main {
    public static void main(String[] args) {
        try {
            ArregloUsuarios arregloUsuarios = new ArregloUsuarios();
            ArregloAlumno arregloA = new ArregloAlumno();
            ArregloCurso arregloCurso = new ArregloCurso();
            ArregloRetiro arregloRetiro = new ArregloRetiro();
            ArregloMatricula arregloMatricula = new ArregloMatricula();

            final MenuPrincipalEvent menuPrincipalEvent = getMenuPrincipalEvent(
                    arregloA,
                    arregloCurso,
                    arregloRetiro,
                    arregloMatricula);
            MenuPrincipal menuPrincipal = new MenuPrincipal(menuPrincipalEvent);

            LoginEvent loginEvent = new LoginEvent(arregloUsuarios,menuPrincipal);
            new Login(loginEvent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MenuPrincipalEvent getMenuPrincipalEvent(ArregloAlumno arregloA,
                                                            ArregloCurso arregloCurso,
                                                            ArregloRetiro arregloRetiro,
                                                            ArregloMatricula arregloMatricula
                                                            ) {

        ConsultaAlumnosCursoEvent consultaAlumnoCursoEvent = new ConsultaAlumnosCursoEvent();
        ConsultaMatriculasRetirosEvent consultaMatriculasRetirosEvent = new ConsultaMatriculasRetirosEvent(arregloMatricula,arregloA,arregloCurso,arregloRetiro);
        MantenimientoAlumnoEvent mantenimientoAlumnoEvent = new MantenimientoAlumnoEvent(arregloA);
        MantenimientoCursoEvent mantenimientoCursoEvent  = new MantenimientoCursoEvent(arregloCurso,arregloMatricula);
        RegistroMatriculaEvent registroMatriculaEvent = new RegistroMatriculaEvent(arregloMatricula,arregloA,arregloCurso);
        RegistroRetiroEvent registroRetiroEvent = new RegistroRetiroEvent(arregloMatricula,arregloA,arregloCurso,arregloRetiro);
        ReporteAlumnnosPendienteEvent reporteAlumnnosPendienteEvent = new ReporteAlumnnosPendienteEvent(arregloA);
        ReporteAlummnosVigentesEvent reporteAlummnosVigentesEvent = new ReporteAlummnosVigentesEvent(arregloA);
        ReporteMatriculasCursoEvent reporteMatriculasCursoEvent = new ReporteMatriculasCursoEvent(arregloMatricula,arregloA,arregloCurso);


        return new MenuPrincipalEvent(
                consultaAlumnoCursoEvent,
                consultaMatriculasRetirosEvent,
                mantenimientoAlumnoEvent,
                mantenimientoCursoEvent,
                registroMatriculaEvent,
                registroRetiroEvent,
                reporteAlumnnosPendienteEvent,
                reporteAlummnosVigentesEvent,
                reporteMatriculasCursoEvent,
                arregloA,
                arregloCurso,
                arregloRetiro,
                arregloMatricula);
    }
}
