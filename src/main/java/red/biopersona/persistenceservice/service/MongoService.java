package red.biopersona.persistenceservice.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Omar Barrera Valentin
 */

@Slf4j
@Service
public class MongoService {

	@Autowired
	MongoTemplate mongoTemplate;

	// Datos del cliente mongoDB
	MongoClient clientGlobal = null;

	// Coleccion con los datos de los clientes
	MongoCollection<Document> clientsAllowedGlobal = null;

	// Constructor de la clase
	public MongoService(@Value("${spring.data.mongodb.database:clients}") String database,
			@Value("${spring.data.mongodb.host:localhost}") String host,
			@Value("${spring.data.mongodb.port:27017}") int port) {
		ConnectionString connectionString = new ConnectionString("mongodb://" + host + ":" + port + "/" + database);
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
		clientGlobal = MongoClients.create(settings);
		MongoDatabase _databaseGlobal = clientGlobal.getDatabase("clients");
		clientsAllowedGlobal = _databaseGlobal.getCollection("companies");
	}

	/***
	 * Metodo que permite validar si es posible operar sobre el cliente
	 * 
	 * @param Client nombre del cliente
	 * @return true, si cumple con la peticion
	 */
	public boolean canOperateTheClient(String Client) {
		boolean resultado = false;
		Bson filtro = Filters.and(Filters.eq("llave", Client), Filters.eq("active", true),
				Filters.or(Filters.exists("maxDateUso", false), Filters.gte("maxDateUso", new Date()))

		);
		List<Document> clients_lists = clientsAllowedGlobal.find(filtro).limit(1).into(new ArrayList<Document>());
		if (!clients_lists.isEmpty()) {
			resultado = true;
		}
		return resultado;
	}

	/**
	 * Metodo para validar la lista de los clientes disponibles
	 * 
	 * @return listado
	 */
	public List<Document> getClientesDisponibles() {
		Bson filtro = Filters.and(Filters.eq("active", true),
				Filters.or(Filters.exists("maxDateUso", false), Filters.gte("maxDateUso", new Date()))

		);
		return clientsAllowedGlobal.find(filtro).into(new ArrayList<Document>());
	}

	/**
	 * Almacena en la tabla relaciones, la muestra con la persona
	 * 
	 * @param nameDB            nombre del cliente o de su bd
	 * @param Template          plantilla Neuro
	 * @param Natural           imagen natural
	 * @param Token             token facial
	 * @param Minex             minex de las huellas
	 * @param IdBiometricPerson idde la persona
	 * @param tipo              tipo de muestra
	 * @param Segmentation      segmenta el almacenado
	 * @return id del registro
	 */
	public String SaveRelationShips(String nameDB, ObjectId Template, ObjectId Natural, ObjectId Token, ObjectId Minex,
			String IdBiometricPerson, String tipo, String Segmentation) {
		MongoDatabase _database = clientGlobal.getDatabase(nameDB);
		MongoCollection<Document> relationships = _database.getCollection("relationships");
		String _id = ObjectId.get().toString();
		Document insert = new Document();
		insert.append("_id", _id);

		if (IdBiometricPerson != null) {
			insert.append("IdBiometricPerson", IdBiometricPerson);
			_id = IdBiometricPerson;
		} else {
			insert.append("IdBiometricPerson", _id);
		}

		insert.append("template", Template.toString());
		if (Natural != null) {
			insert.append("natural", Natural.toString());
		}
		if (Segmentation != null) {
			insert.append("Segmentation", Segmentation);
		}

		if (Token != null) {
			insert.append("token", Token.toString());
		}

		insert.append("tipo", tipo);
		insert.append("createdAt", new Date());

		if (Minex != null) {
			insert.append("minex", Minex.toString());
		}

		relationships.insertOne(insert);
		return _id;
	}

	public Object[] DeleteBiometric(String RmBiometricId, String NameDB) {
		MongoDatabase _database = clientGlobal.getDatabase(NameDB);
		MongoCollection<Document> relationships = _database.getCollection("relationships");
		Object[] result = new Object[2];
		result[0] = false;
		List<String> idsTemplates = new ArrayList<>();

		Bson FiltroPersonIdSample = Filters.eq("template", RmBiometricId);
		Bson FiltroPersonId = Filters.eq("IdBiometricPerson", RmBiometricId);

		List<Document> ListFiltroPersonIdSample = relationships.find(FiltroPersonIdSample)
				.into(new ArrayList<Document>());
		if (!ListFiltroPersonIdSample.isEmpty()) {
			result[0] = true;
			for (Document data : ListFiltroPersonIdSample) {
				String _id = data.getString("_id");
				if (data.containsKey("template")) {
					String template = data.getString("template");
					RemoveFile(NameDB, template);
					idsTemplates.add(template);
				}

				if (data.containsKey("natural")) {
					String natural = data.getString("natural");
					RemoveFile(NameDB, natural);
				}

				if (data.containsKey("token")) {
					String token = data.getString("token");
					RemoveFile(NameDB, token);
				}

				if (data.containsKey("minex")) {
					String token = data.getString("minex");
					RemoveFile(NameDB, token);
				}
				relationships.deleteMany(new Document("_id", _id));
			}
		}

		List<Document> ListFiltroPersonId = relationships.find(FiltroPersonId).into(new ArrayList<Document>());
		if (!ListFiltroPersonId.isEmpty()) {
			result[0] = true;
			for (Document data : ListFiltroPersonId) {
				String _id = data.getString("_id");
				if (data.containsKey("template")) {
					String template = data.getString("template");
					RemoveFile(NameDB, template);
					idsTemplates.add(template);
				}

				if (data.containsKey("natural")) {
					String natural = data.getString("natural");
					RemoveFile(NameDB, natural);
				}

				if (data.containsKey("token")) {
					String token = data.getString("token");
					RemoveFile(NameDB, token);
				}

				if (data.containsKey("minex")) {
					String token = data.getString("minex");
					RemoveFile(NameDB, token);
				}
				relationships.deleteMany(new Document("_id", _id));
			}
		}

		result[1] = idsTemplates;
		return result;
	}

	public boolean RemoveFile(String NameDB, String _id) {
		MongoDatabase _database = clientGlobal.getDatabase(NameDB);
		boolean result = false;

		MongoCollection<Document> data_files = _database.getCollection("data.files");
		MongoCollection<Document> data_chunks = _database.getCollection("data.chunks");

		Bson FiltroDataFile = Filters.eq("_id", new ObjectId(_id));
		Bson FiltroDataChunks = Filters.eq("files_id", new ObjectId(_id));
		List<Document> data_filesLists = data_files.find(FiltroDataFile).into(new ArrayList<Document>());
		if (!data_filesLists.isEmpty()) {
			data_files.deleteMany(new Document("_id", new ObjectId(_id)));
			result = true;
		}
		List<Document> data_ChunksLists = data_chunks.find(FiltroDataChunks).into(new ArrayList<Document>());
		if (!data_ChunksLists.isEmpty()) {
			data_chunks.deleteMany(new Document("files_id", new ObjectId(_id)));
			result = true;
		}
		return result;
	}

	public void addEventClient(String Client, String action) {
		Bson filtro = Filters.eq("clientName", Client);
		List<Document> clients_lists = clientsAllowedGlobal.find(filtro).limit(1).into(new ArrayList<Document>());
		if (!clients_lists.isEmpty()) {
			String _id = clients_lists.get(0).getString("_id");
			int ActionCount = 0;
			try {
				ActionCount = clients_lists.get(0).getInteger(action + "Count");
			} catch (Exception e) {
			}
			Document query = new Document();
			query.append("_id", _id);
			Document setData = new Document();
			setData.append(action + "Count", ActionCount + 1);
			Document update = new Document();
			update.append("$set", setData);
			clientsAllowedGlobal.updateOne(query, update);
		}
	}

	public ObjectId saveTemplate(String nombre, byte[] sample, String nameDB) {
		ObjectId id = null;
		try {
			MongoDatabase _database = clientGlobal.getDatabase(nameDB);
			GridFSBucket fs = GridFSBuckets.create(_database, "data");
			ByteArrayInputStream bis_huella = new ByteArrayInputStream(sample);
			id = fs.uploadFromStream(nombre, bis_huella);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Err SaveImage :" + e.getMessage());
		}
		return id;
	}

}
