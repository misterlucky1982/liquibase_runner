package by.misterlucky.liquibase;

public class ChangeSet {
	
	private String name;
	private String author;
	private String script;
	private String requirenmentLevel;
	
	public ChangeSet(String name, String author, String script, String requirenmentLevel){
		if(name==null)throw new LiquibaseException("name can`t be null");
		if(author==null)throw new LiquibaseException("author can`t be null");
		this.author=author;
		this.name=name;
		this.script=script;
		this.requirenmentLevel = requirenmentLevel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangeSet other = (ChangeSet) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * @return the requirenmentLevel
	 */
	public String getRequirenmentLevel() {
		return requirenmentLevel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChangeSet [name=" + name + ", author=" + author + ", script=" + script + ", requirenmentLevel="
				+ requirenmentLevel + "]";
	}
}
