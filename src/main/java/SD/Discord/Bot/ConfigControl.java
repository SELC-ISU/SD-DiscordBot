package SD.Discord.Bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class ConfigControl {

	private YamlReader yamlR;
	private YamlWriter yamlW;
	
	/**
	 * Constructs a Config Control and it's reader and writer
	 * @param yamlReader
	 * 		The YamlReader
	 * @param yamlWriter
	 * 		The YamlWriter
	 */
	public ConfigControl(YamlReader yamlReader, YamlWriter yamlWriter) {
		yamlR = yamlReader;
		yamlW = yamlWriter;
	}
	
	/**
	 * Returns a string of the config. Mostly used for testing.
	 * @return
	 * 		The string array of config elements
	 */
	public List<String> getConfig(){
		List<String> stringList = new ArrayList<String>();
		boolean run = true;
		while (run) {
			try {
				Object obj = yamlR.read();
				if (obj == null) break;
				stringList.add(obj.toString());
			} catch (YamlException e) {
				e.printStackTrace();
			}
		}
		return stringList;
	}
	
	/**
	 * Returns the config as a list of objects.
	 * @return
	 * 		The object list of the config elements
	 */
	public List<Object> getConfigObjects(){
		List<Object> objList = new ArrayList<Object>();
		boolean run = true;
		while (run) {
			try {
				Object obj = yamlR.read();
				if (obj == null) break;
				objList.add(obj);
			} catch (YamlException e) {
				e.printStackTrace();
			}
		}
		return objList;
	}
	
	/**
	 * Returns a list of the possible config keys. (just removes the brackets)
	 * @param configList
	 * 		The list of objects (usually HashMap) to look through.
	 * @return
	 * 		The string of keys
	 */
	private String keyList(List<Object> configList) {
		String newConfig = removeWhitespace(configList.toString());
		StringBuilder keyListBuilder = new StringBuilder();
		for (char c : newConfig.toCharArray()) {
			if (c != '{' && c != '}' && c != '[' && c != ']') {
				keyListBuilder.append(c);
			}
		}
		String keyList = keyListBuilder.toString();
		return keyList;
	}
	
	/**
	 * Returns the object at a certain key
	 * @param configList
	 * 		The list of objects to look through
	 * @param key
	 * 		The key to search for the value
	 * @return
	 * 		The result object
	 */
	@SuppressWarnings("unchecked")
	public Object getValue(List<Object> configList, String key) {
		Object returnObj = null;
		List<Object> finalList = new ArrayList<Object>();
		String keyList = keyList(configList);
		HashMap<String, Object> map = configSplit(keyList);
		for (Object obj : configList) {
			if (obj instanceof HashMap<?, ?>) {
				HashMap<String, Object> hashmap = (HashMap<String, Object>) obj;
				boolean hasList = false;
				for (Object all : hashmap.values()) {
					if (all instanceof List<?>) {
						hasList = true;
					}
				}
				if (hasList) {
					HashMap<String, List<Object>> mapList = (HashMap<String, List<Object>>) obj;
					if (mapList.get(key) != null) {
						for (Object o : mapList.values()) {
							finalList.add(o);
						}
						returnObj = finalList;
						break;
					}
					break;
				}
				returnObj = map.get(key);
			}
		}
		return returnObj;
	}
	
	/**
	 * Adds an object to the configuration file
	 * @param configList
	 * 		The list of objects of the current config.
	 * @param key
	 * 		The key to store the object under
	 * @param value
	 * 		The object to store
	 */
	public void addValue(List<Object> configList, String key, Object value) {
		try {
			HashMap<String, Object> list = new HashMap<String, Object>();
			list.putAll(configSplit(keyList(configList)));
			if (!list.containsKey(key)) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(key, value);
				yamlW.write(map);
			}
		} catch (YamlException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 		Method that has since become useless because of merge with getValue method.
	 * 
	 * 
	@SuppressWarnings("unchecked")
	public List<Object> getList(List<Object> configList, String key){
		List<Object> finalList = new ArrayList<Object>();
		for (Object obj : configList) {
			if (obj instanceof HashMap<?, ?>) {
				HashMap<String, Object> map = (HashMap<String, Object>) obj;
				boolean hasList = false;
				for (Object all : map.values()) {
					if (all instanceof List<?>) {
						hasList = true;
					}
				}
				if (hasList) {
					HashMap<String, List<Object>> mapList = (HashMap<String, List<Object>>) obj;
					if (mapList.get(key) != null) {
						for (Object o : mapList.values()) {
							finalList.add(o);
						}
					}
				}
			}
		}
		return finalList;
	}
	*/
	
	/**
	 * Returns the current writer
	 * @return
	 * 		The writer
	 */
	public YamlWriter getWriter() {
		return yamlW;
	}
	
	/**
	 * Returns the current reader
	 * @return
	 * 		The reader
	 */
	public YamlReader getReader() {
		return yamlR;
	}
	
	/**
	 * Sets a new writer for the config control
	 * @param writer
	 * 		The new writer
	 */
	public void setWriter(YamlWriter writer) {
		yamlW = writer;
	}
	
	/**
	 * Sets a new reader for the config control
	 * @param reader
	 * 		The new reader
	 */
	public void setReader(YamlReader reader) {
		yamlR = reader;
	}
	
	private String removeWhitespace(String string) {
		StringBuilder s = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (c != ' ') {
				s.append(c);
			}
		}
		return s.toString();
	}
	
	private HashMap<String, Object> configSplit(String config){
		HashMap<String, Object> finalList =  new HashMap<String, Object>();
		String[] list = config.split(",");
		for (String s : list) {
			String[] keyAndVal = s.split("=", 2);
			if (keyAndVal.length < 2) continue;
			finalList.put(keyAndVal[0], keyAndVal[1]);
		}
		return finalList;
	}
	
}
