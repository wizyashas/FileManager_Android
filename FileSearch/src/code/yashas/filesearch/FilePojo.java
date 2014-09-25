package code.yashas.filesearch;

import java.io.Serializable;

/**
 * 
 * @author yashas
 *
 */
public class FilePojo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 726858408817448904L;
	private String fileName;
	private String filePath;

	public FilePojo(String fileName, String filePath) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilePojo other = (FilePojo) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FilePojo [fileName=" + fileName + ", filePath=" + filePath
				+ "]";
	}
	
	
	
	

}
