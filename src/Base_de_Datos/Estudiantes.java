package Base_de_Datos;


import java.sql.*;
import java.util.Scanner;

public class Estudiantes {
    public static void main(String[] args) {
        // Datos de conexi�n a la base de datos (ajusta estos valores seg�n tu configuraci�n)
        String url = "jdbc:mysql://localhost:3306/universidad";
        String usuario = "root";
        String pass = "";

        Scanner scanner = new Scanner(System.in);

        try {
            // Establecer la conexi�n a la base de datos
            Connection conexion = DriverManager.getConnection(url, usuario, pass);

            while (true) {
                System.out.println("Men�:");
                System.out.println("1. Mostrar estudiantes");
                System.out.println("2. Agregar estudiante");
                System.out.println("3. Editar estudiante");
                System.out.println("4. Eliminar estudiante");
                System.out.println("5. Mostrar mayores");
                System.out.println("6. Salir");
                System.out.print("Selecciona una opci�n: ");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva l�nea

                switch (opcion) {
                    case 1:
                        mostrarEstudiantes(conexion);
                        break;
                    case 2:
                        agregarEstudiante(conexion, scanner);
                        break;
                    case 3:
                        editarEstudiante(conexion, scanner);
                        break;
                    case 4:
                        eliminarEstudiante(conexion, scanner);
                        break;
                    case 5:
                        int edad;
                        System.out.println("Ingrese la edad mínima que quiere mostrar:");
                        edad = scanner.nextInt();
                        mostrarMayores(conexion, edad);
                        break;
                    case 6:
                        // Cerrar la conexi�n y salir del programa
                        conexion.close();
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opci�n no v�lida. Por favor, elige una opci�n v�lida.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Funci�n para mostrar estudiantes
    // Esta funci�n muestra todos los estudiantes almacenados en la base de datos.
    private static void mostrarEstudiantes(Connection conexion) throws SQLException {
        // Crea una declaraci�n SQL para ejecutar una consulta de selecci�n.
        Statement statement = conexion.createStatement();

        // Define la consulta SQL para obtener todos los estudiantes.
        String consulta = "SELECT * FROM estudiantes";

        // Ejecuta la consulta SQL y almacena los resultados en un ResultSet.
        ResultSet resultado = statement.executeQuery(consulta);

        // Imprime una cabecera de columnas para los datos de los estudiantes.
        System.out.println("ID\tNombre\tApellido\tLegajo\tDNI\tFecha de nacimiento\tDirecci�n\tTel�fono\tEmail");

        // Itera a trav�s de los resultados y muestra los datos de cada estudiante en forma de tabla.
        while (resultado.next()) {
            int id = resultado.getInt("id");
            String nombre = resultado.getString("nombre");
            String apellido = resultado.getString("apellido");
            String legajo = resultado.getString("legajo");
            String dni = resultado.getString("dni");
            String fechaNacimiento = resultado.getString("fecha_nacimiento");
            String direccion = resultado.getString("direccion");
            String telefono = resultado.getString("telefono");
            String email = resultado.getString("email");

            // Imprime los datos del estudiante con tabulaciones para formatear como una tabla.
            System.out.println(id + "\t" + nombre + "\t" + apellido + "\t" + legajo + "\t" + dni + "\t" +
                    fechaNacimiento + "\t" + direccion + "\t" + telefono + "\t" + email);
        }

        // Cierra el ResultSet y la declaraci�n para liberar recursos.
        resultado.close();
        statement.close();
    }


    // Funci�n para agregar un estudiante
    // Esta funci�n agrega un nuevo estudiante a la base de datos.
    private static void agregarEstudiante(Connection conexion, Scanner scanner) throws SQLException {
        // Solicita al usuario que ingrese los detalles del estudiante
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Legajo: ");
        String legajo = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();
        System.out.print("Direcci�n: ");
        String direccion = scanner.nextLine();
        System.out.print("Tel�fono: ");
        String telefono = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Consulta SQL para insertar un nuevo estudiante en la base de datos.
        // Utiliza signos de interrogaci�n como marcadores de posici�n para los valores.
        String consulta = "INSERT INTO estudiantes (nombre, apellido, legajo, dni, fecha_nacimiento, direccion, telefono, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Crea un PreparedStatement para ejecutar la consulta SQL con valores reales.
        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
        preparedStatement.setString(1, nombre); // Asigna el valor de nombre al primer marcador de posici�n
        preparedStatement.setString(2, apellido); // Asigna el valor de apellido al segundo marcador de posici�n
        preparedStatement.setString(3, legajo); // Asigna el valor de legajo al tercer marcador de posici�n
        preparedStatement.setString(4, dni); // Asigna el valor de dni al cuarto marcador de posici�n
        preparedStatement.setString(5, fechaNacimiento); // Asigna el valor de fecha de nacimiento al quinto marcador de posici�n
        preparedStatement.setString(6, direccion); // Asigna el valor de direcci�n al sexto marcador de posici�n
        preparedStatement.setString(7, telefono); // Asigna el valor de tel�fono al s�ptimo marcador de posici�n
        preparedStatement.setString(8, email); // Asigna el valor de email al octavo marcador de posici�n

        // Ejecuta la consulta y obtiene el n�mero de filas afectadas.
        int filasAfectadas = preparedStatement.executeUpdate();

        // Verifica si la inserci�n fue exitosa y muestra un mensaje apropiado.
        if (filasAfectadas > 0) {
            System.out.println("Estudiante agregado exitosamente.");
        } else {
            System.out.println("No se pudo agregar el estudiante.");
        }

        // Cierra el PreparedStatement para liberar recursos.
        preparedStatement.close();
    }


    // Funci�n para editar un estudiante
    // Esta funci�n permite editar un estudiante existente en la base de datos.
    private static void editarEstudiante(Connection conexion, Scanner scanner) throws SQLException {
        // Solicita al usuario que ingrese el ID del estudiante que desea editar.
        System.out.print("ID del estudiante a editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva l�nea despu�s de leer un entero.

        // Verificar si el estudiante con el ID especificado existe en la base de datos.
        String consultaExistencia = "SELECT * FROM estudiantes WHERE id = ?";
        PreparedStatement preparedStatementExistencia = conexion.prepareStatement(consultaExistencia);
        preparedStatementExistencia.setInt(1, id); // Establece el valor del marcador de posici�n.
        ResultSet resultadoExistencia = preparedStatementExistencia.executeQuery();

        // Si no se encuentra ning�n estudiante con el ID proporcionado, muestra un mensaje y sale de la funci�n.
        if (!resultadoExistencia.next()) {
            System.out.println("El estudiante no existe.");
            preparedStatementExistencia.close();
            return;
        }

        // Solicita al usuario ingresar los nuevos detalles del estudiante.
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nuevo apellido: ");
        String nuevoApellido = scanner.nextLine();
        System.out.print("Nuevo legajo: ");
        String nuevoLegajo = scanner.nextLine();
        System.out.print("Nuevo DNI: ");
        String nuevoDNI = scanner.nextLine();
        System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD): ");
        String nuevaFechaNacimiento = scanner.nextLine();
        System.out.print("Nueva direcci�n: ");
        String nuevaDireccion = scanner.nextLine();
        System.out.print("Nuevo tel�fono: ");
        String nuevoTelefono = scanner.nextLine();
        System.out.print("Nuevo email: ");
        String nuevoEmail = scanner.nextLine();

        // Consulta SQL para actualizar los datos del estudiante en la base de datos.
        String consulta = "UPDATE estudiantes SET nombre = ?, apellido = ?, legajo = ?, dni = ?, " +
                "fecha_nacimiento = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";

        // Crea un PreparedStatement para ejecutar la consulta SQL con valores reales.
        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
        preparedStatement.setString(1, nuevoNombre); // Asigna el valor del nuevo nombre.
        preparedStatement.setString(2, nuevoApellido); // Asigna el valor del nuevo apellido.
        preparedStatement.setString(3, nuevoLegajo); // Asigna el valor del nuevo legajo.
        preparedStatement.setString(4, nuevoDNI); // Asigna el valor del nuevo DNI.
        preparedStatement.setString(5, nuevaFechaNacimiento); // Asigna el valor de la nueva fecha de nacimiento.
        preparedStatement.setString(6, nuevaDireccion); // Asigna el valor de la nueva direcci�n.
        preparedStatement.setString(7, nuevoTelefono); // Asigna el valor del nuevo tel�fono.
        preparedStatement.setString(8, nuevoEmail); // Asigna el valor del nuevo email.
        preparedStatement.setInt(9, id); // Asigna el ID del estudiante que se va a editar.

        // Ejecuta la consulta SQL y obtiene el n�mero de filas afectadas.
        int filasAfectadas = preparedStatement.executeUpdate();

        // Verifica si la edici�n fue exitosa y muestra un mensaje apropiado.
        if (filasAfectadas > 0) {
            System.out.println("Estudiante editado exitosamente.");
        } else {
            System.out.println("No se pudo editar el estudiante.");
        }

        // Cierra el PreparedStatement para liberar recursos.
        preparedStatement.close();
    }


    // Funci�n para eliminar un estudiante
    // Esta funci�n permite eliminar un estudiante de la base de datos.
    private static void eliminarEstudiante(Connection conexion, Scanner scanner) throws SQLException {
        // Solicita al usuario que ingrese el ID del estudiante que desea eliminar.
        System.out.print("ID del estudiante a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva l�nea despu�s de leer un entero.

        // Verificar si el estudiante con el ID especificado existe en la base de datos.
        String consultaExistencia = "SELECT * FROM estudiantes WHERE id = ?";
        PreparedStatement preparedStatementExistencia = conexion.prepareStatement(consultaExistencia);
        preparedStatementExistencia.setInt(1, id); // Establece el valor del marcador de posici�n.
        ResultSet resultadoExistencia = preparedStatementExistencia.executeQuery();

        // Si no se encuentra ning�n estudiante con el ID proporcionado, muestra un mensaje y sale de la funci�n.
        if (!resultadoExistencia.next()) {
            System.out.println("El estudiante no existe.");
            preparedStatementExistencia.close();
            return;
        }

        // Consulta SQL para eliminar el estudiante de la base de datos.
        String consulta = "DELETE FROM estudiantes WHERE id = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
        preparedStatement.setInt(1, id); // Establece el valor del marcador de posici�n.

        // Ejecuta la consulta SQL y obtiene el n�mero de filas afectadas.
        int filasAfectadas = preparedStatement.executeUpdate();

        // Verifica si la eliminaci�n fue exitosa y muestra un mensaje apropiado.
        if (filasAfectadas > 0) {
            System.out.println("Estudiante eliminado exitosamente.");
        } else {
            System.out.println("No se pudo eliminar el estudiante.");
        }

        // Cierra el PreparedStatement para liberar recursos.
        preparedStatement.close();
    }

    public static void mostrarMayores(Connection conexion, int edad) throws SQLException {
        Statement statement = conexion.createStatement();

        // Define la consulta SQL para obtener todos los estudiantes.
        String consulta = "SELECT * FROM estudiantes";

        // Ejecuta la consulta SQL y almacena los resultados en un ResultSet.
        ResultSet resultado = statement.executeQuery(consulta);

        // Imprime una cabecera de columnas para los datos de los estudiantes.
        System.out.println("ID\tNombre\tApellido\tLegajo\tDNI\tFecha de nacimiento\tDirección\tTeléfono\tEmail");

        // Itera a trav�s de los resultados y muestra los datos de cada estudiante en forma de tabla.
        while (resultado.next()) {
            String fechaNacimiento = resultado.getString("fecha_nacimiento");
            if (convertirAEdad(fechaNacimiento) > edad) {

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String legajo = resultado.getString("legajo");
                String dni = resultado.getString("dni");

                String direccion = resultado.getString("direccion");
                String telefono = resultado.getString("telefono");
                String email = resultado.getString("email");

                // Imprime los datos del estudiante con tabulaciones para formatear como una tabla.
                System.out.println(id + "\t" + nombre + "\t" + apellido + "\t" + legajo + "\t" + dni + "\t" +
                        fechaNacimiento + "\t" + direccion + "\t" + telefono + "\t" + email);
            }
        }

        // Cierra el ResultSet y la declaraci�n para liberar recursos.
        resultado.close();
        statement.close();
    }

    public static int convertirAEdad(String fechaNacimiento){
        String edadActual = String.valueOf(fechaNacimiento.charAt(0)) +
                String.valueOf(fechaNacimiento.charAt(1)) +
                String.valueOf(fechaNacimiento.charAt(2)) +
                String.valueOf(fechaNacimiento.charAt(3));

        int edadComparable = Integer.parseInt(edadActual);

        return 2023 - edadComparable;
    }
}
