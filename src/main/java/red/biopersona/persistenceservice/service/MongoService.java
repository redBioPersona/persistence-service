package red.biopersona.persistenceservice.service;

import java.io.ByteArrayInputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MongoService {

	@Autowired
	MongoTemplate mongoTemplate;

	MongoClient mongoClient = null;

	public MongoService(
			@Value("${spring.data.mongodb.database:clientes}") String database,
			@Value("${spring.data.mongodb.host:localhost}") String host,
			@Value("${spring.data.mongodb.port:27017}") int port) {
		ConnectionString connectionString = new ConnectionString("mongodb://" + host + ":" + port + "/" + database);
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
		mongoClient = MongoClients.create(settings);
	}

	public ObjectId saveTemplate(String nombre, byte[] sample, String nameDB) {
		ObjectId id = null;
		try {
			MongoDatabase _database = mongoClient.getDatabase(nameDB);
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
