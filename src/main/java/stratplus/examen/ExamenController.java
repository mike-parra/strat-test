package stratplus.examen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/examen")
public class ExamenController {

	@Inject
	CalculatorWsClient calculatorWsClient;

	@GET
	@Path("/suma/{num1}/{num2}")
	public Response add(@PathParam(value = "num1") int num1, @PathParam(value = "num2") int num2) {
		int result = calculatorWsClient.add(num1, num2);
		return Response.ok(Map.of("resultado_suma", result)).build();
	}

	@GET
	@Path("/division/{num1}/{num2}")
	public Response divide(@PathParam(value = "num1") int num1, @PathParam(value = "num2") int num2) {
		int result = calculatorWsClient.divide(num1, num2);
		return Response.ok(Map.of("resultado_division", result)).build();
	}

	@GET
	@Path("/multiplicacion/{num1}/{num2}")
	public Response multiply(@PathParam(value = "num1") int num1, @PathParam(value = "num2") int num2) {
		int result = calculatorWsClient.multiply(num1, num2);
		return Response.ok(Map.of("resultado_multiplicacion", result)).build();
	}

	@GET
	@Path("/randomuser")
	public Response saludarQuery() {

		try {

			System.out.println("... Iniciando ....");

			String urlString = "https://randomuser.me/api/";

			URL url = new URL(urlString);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "/");

			System.out.println("Leyendo respuesta....");

			String respuestaRandomUserAPI = "";

			/*
			 * 
			 * BLOQUE PARA LEER LA RESPUESTA DEL API
			 * 
			 **/

			respuestaRandomUserAPI = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
					.collect(Collectors.joining("\n"));

			System.out.println("::: RESPUESTA DEL API OBTENIDA:::");

			System.out.println(respuestaRandomUserAPI);

			System.out.println(" FIN ....");

			ObjectMapper mapper = new ObjectMapper();

			JsonNode json = mapper.readTree(respuestaRandomUserAPI);

			return Response.ok(Map.of("resultado", json)).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.noContent().build();
		}

	}

	@GET
	@Path("/lista-marvel")
	public Response listaPersonajeMarvel() throws IOException, InterruptedException {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
				"https://gateway.marvel.com/v1/public/characters?apikey=ce0e26bf2f195432fdf314e0a488dcc2&ts=1710457831&hash=942e8f7595e7d9283c7050120d3ac493"))
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JsonNode json = mapper.readTree(response.body());

		String asString = json.get("data").get("results").toPrettyString();

		Integer total = json.get("data").get("total").asInt();

		List<PersonajeMarvel> personajes = mapper.readValue(asString, new TypeReference<List<PersonajeMarvel>>() {
		});

		Map<String, Object> total_personajes = new HashMap<>();

		total_personajes.put("personajes", personajes);
		total_personajes.put("total", total);

		Log.info(total_personajes);

		return Response.ok(total_personajes).build();
	}

	@GET
	@Path("/lista-marvel/doctor-strange/1009282")
	public Response doctorStrangeIno() throws IOException, InterruptedException {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
				"https://gateway.marvel.com/v1/public/characters/1009282?apikey=ce0e26bf2f195432fdf314e0a488dcc2&ts=1710457831&hash=942e8f7595e7d9283c7050120d3ac493"))
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JsonNode json = mapper.readTree(response.body());

		String asString = json.get("data").get("results").toPrettyString();

		Map<String, Object> personaje = new HashMap<>();
		personaje.put("Dr_Strange", json.get("data").get("results").get(0));

		Log.info(personaje);

		return Response.ok(personaje).build();
	}
}
