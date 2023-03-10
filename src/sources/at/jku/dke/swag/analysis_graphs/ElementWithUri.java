package at.jku.dke.swag.analysis_graphs;

import java.util.Objects;

public class ElementWithUri {
    private String uri;

    public ElementWithUri(String uri) {
        this.uri = uri;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementWithUri that = (ElementWithUri) o;
        return Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }

    public String toString(){
        return this.getUri();
    }
}
