package conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author JasonChen1
 *
 */
public class PropConfig {

	private static Properties props;
	private static Logger logger = Logger.getLogger(PropConfig.class);
	
	public static String getValueFromProps(String key){
		if(PropConfig.props == null){
			PropConfig.props = new Properties();;
			InputStream fis = null;
			try {
				fis = PropConfig.class.getClassLoader().getResourceAsStream("conf/config.properties");
				props.load(fis);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}finally{
				if(fis != null){
					try {
						fis.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		return props.getProperty(key);
	}
}
