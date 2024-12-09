/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Kevin
 */
public class ApiRest {
    
    private final String apiUrl = "http://localhost//MisProyectos/Quinto/API.php";
    
    //Método GET para obtener estudiantes
    public String getStudents(){
    StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                reader.close();
            } else{
                System.out.println("Error de conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    return result.toString();
    }
    
    private String sendRequest(String method, String cedula, String nombre, String apellido, String direccion, String telefono){
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            
            String params = "cedula=" + cedula + "&nombre=" +nombre + "&apellido=" +apellido + "&direccion=" + direccion + "&telefono=" +telefono;
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes(params);
            writer.flush();
            writer.close();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine())!= null){
                response.append(line);
            }
            reader.close();
            return response.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar la solicitud";
        }
    }
    
    //Método POST guardar
    public String saveStudent(String cedula, String nombre, String apellido, String direccion, String telefono){
        return sendRequest("POST", cedula, nombre, apellido, direccion, telefono);
    }
    //Método PUT editar
    public String updateStudent(String cedula, String nombre, String apellido, String direccion, String telefono){
        return sendRequest("PUT", cedula, nombre, apellido, direccion, telefono);
    }
    
    //Método DELETE
    
    public String deleteStudent(String cedula){
        try {
            URL url = new URL(apiUrl + "?cedula=" + cedula);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } else{
                return "Error: " + conn.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar el estudiante";
        }
    
    }
    
    public String getStudentByCedula(String cedula){
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl + "?cedula=" + cedula);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                reader.close();
                
            } else{
                System.out.println("Error: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    
    }
    
    
}
