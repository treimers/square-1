package net.treimers.square1.model.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.hjson.JsonValue;
import org.hjson.Stringify;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.Square1Data;

/**
 * Instances of this class are used to persist or restore Square-1 data to or from a file respectively.
 */
public class FileStore implements DataStore {
	/** The store / restore File. */
	private File file;
	/** ObjectMapper used to convert data to or from Json representation. */
	private ObjectMapper mapper;

	/**
	 * Create a new FileStore object.
	 */
	public FileStore() {
		mapper = new ObjectMapper();
	}

	/**
	 * Sets the store / restore File.
	 * 
	 * @param file the store / restore File.
	 */
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void store(Square1Data data) throws Square1Exception {
		try {
			String json = mapper.writeValueAsString(data);
			String hjson = JsonValue.readHjson(json).toString(Stringify.HJSON);
			Files.writeString(file.toPath(), hjson, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new Square1Exception(e);
		}
	}

	@Override
	public Square1Data restore() throws Square1Exception {
		try {
			String hjson = Files.readString(file.toPath());
			String json = JsonValue.readHjson(hjson).toString();
			return mapper.readValue(json, Square1Data.class);
		} catch (IOException e) {
			throw new Square1Exception(e);
		}
	}

}
