package at.jku.dke.swag.md_elements;

public abstract class MappableMDElement extends MDElement{

    private String query;

    public MappableMDElement(String uri) {
        super(uri);
    }

    public MappableMDElement(String uri, String query) {
        super(uri);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
