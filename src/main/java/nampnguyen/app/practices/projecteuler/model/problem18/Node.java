package nampnguyen.app.practices.projecteuler.model.problem18;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Node implements Cloneable{
    private Integer value;
    private List<Node> next;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        if (next == null) {
            if (other.next != null)
                return false;
        } else if (!next.equals(other.next))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + ((next == null) ? 0 : next.hashCode());
        return result;
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        Node clone = (Node)super.clone();
        clone.setValue(this.value);
        clone.setNext(new ArrayList<>(this.next));
        return clone;
    }

    @Override
    public String toString() {
        return "Node [value=" + value + "]";
    }
    
}
