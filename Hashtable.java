import java.util.ArrayList;

public class Hashtable<k,v>{
    private ArrayList<HashNode<k,v>> slots;
    private int size;
    private double LAMBDA = 0.5;
    private int arrsize = 11;

    /*constructer*/

    public Hashtable(){
        size = 0;
        slots = new ArrayList();
        for(int i=0; i < arrsize; i++ ){
            slots.add(null);
        }
    }

    /*function that returns the slot of a certain key*/
    private int getSlot(String key){
        int slot  = key.hashCode();
        return slot%slots.size();
    }

    /**/
    public boolean containsKey(String key){
        int slot = getSlot(key);
        HashNode node = slots.get(slot);
        while(node!=null){
            if(node.getkey()==key){
                return true;
            }
        }

        return false;
    }

    /*find the key and return it*/
    public String get (String key){
        int slot =  getSlot(key);
        HashNode node = slots.get(slot);

        while(node != null && node.getkey()!=key){
            node.getNext();
        }
        if (node == null){
            return null;
        }

        return node.getvalue();
    }

    protected void grow_array(){
        //make temp that's double size
        ArrayList<HashNode<k,v>> temp = new ArrayList<>();
        //copy elements of prev array to the temp
        int i =0;
        for(i=0; i < arrsize*2; i++){
            temp.add(null);
        }

        for(int j = 0; j < arrsize; j++){
            temp.add(slots.get(j));
        }

        arrsize=arrsize*2;

        //arr now equals to the new array
        slots=temp;


    }

    public void put (String key, String value){


        int slot = getSlot(key);
        HashNode node = slots.get(slot);

        while(node != null && node.getkey()!=key){
            node.getNext();
        }

        if (node!=null){
            node.setvalue(value);
        }
        else{
            if(size/slots.size()>=LAMBDA){
                grow_array();
            }

            HashNode newnode = new HashNode(key,value);
            newnode.setkey(key);
            newnode.setvalue(value);
            newnode.setNext(node);
            slots.set(slot,newnode);
            size++;
        }


    }

    public String remove (String key){

        int slot = getSlot(key);

        HashNode node = slots.get(slot);
        HashNode prev = null;

        while(node.getNext()!= null && node.getkey()!=key){
            prev = node;
            node = node.getNext();
        }

        if(node.getkey()==key && node==null){
            return null;
        }
        if (prev == null){
            slots.set(slot,node.getNext());
        }
        else{
            prev.setNext(node.getNext());
            size--;
        }
        return node.getvalue();
    }

}