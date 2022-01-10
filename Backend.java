// --== CS400 File Header Information ==--
// Name: <Connor Phillips>
// Email: <cjphillips4@wisc.edu>
// Team: <Red>
// Group: <HF>
// TA: <Hang>
// Lecturer: <Florian>
// Notes to Grader: <optional extra notes>
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class Backend  {
    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node {
        public Book data;
        public Node parent; // null for root node
        public Node leftChild; 
        public Node rightChild; 
        public boolean isBlack;
        public Node(Book data) 
        { 
            this.data = data;
            isBlack=false;
        }

        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node. The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * Note that the Node's implementation of toString generates a level
         * order traversal. The toString of the RedBlackTree class below
         * produces an inorder traversal of the nodes / values of the tree.
         * This method will be helpful as a helper for the debugging and testing
         * of your rotation implementation.
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() {
            String output = "[";
            LinkedList<Node> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    public Backend(Reader input)
    {
        BookReader b=new BookReader();
        List<BookInterface> list=b.readDataSet(input);
        for(int i=0;i<list.size();i++)
            insert((Book)list.get(i));
    }

    public Backend(){}
    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */

    public boolean insert(Book data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node newNode = new Node(data);
        if(root == null) 
        { 
            root = newNode; 
            size++;
            root.isBlack=true;
            return true; 
        } // add first node to an empty tree
        else{
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) size++;
            else throw new IllegalArgumentException(
                    "This RedBlackTree already contains that value.");
            root.isBlack=true;
            return returnValue;
        }
    }

    /**
     * Enforces the rules of a RedBlackTree by handling all 3 cases of insertions
     * @param child the Node that has been inserted into tree or the current node called by recursive call in red sibling case
     */
    private void enforceRBTreePropertiesAfterInsert(Node child)
    {
        //Checks if child is red and parent isnt null
        if(!child.isBlack && child.parent!=null) 
        {
            //Checks if parent is red
            if(!child.parent.isBlack)
            {
                //Checks if parent has black sibling and the g p and c relationship is a straight line
                if((child.parent.isLeftChild() && (child.parent.parent.rightChild==null || child.parent.parent.rightChild.isBlack)) || (!child.parent.isLeftChild() && (child.parent.parent.leftChild==null || child.parent.parent.leftChild.isBlack)))
                {
                    //Checks if the straight line of left children
                    if(child.isLeftChild() && child.parent.isLeftChild()){
                        Node parent=child.parent;
                        Node grandparent=child.parent.parent; 
                        //Rotates 
                        rotate(child.parent,child.parent.parent);
                        //Recolors
                        parent.isBlack=true;
                        if(grandparent!=null)
                            grandparent.isBlack=false;
                    }
                    //Checks if the straight line of right children
                    else if(!child.isLeftChild() && !child.parent.isLeftChild()) {
                        Node parent=child.parent;
                        Node grandparent=child.parent.parent;
                        rotate(child.parent,child.parent.parent);
                        parent.isBlack=true;
                        if(grandparent!=null)
                            grandparent.isBlack=false;
                    }
                    //Last case is when c p and g dont have straight line relationship
                    else
                    {
                        //first rotation
                        rotate(child, child.parent); 
                        //second rotation
                        rotate(child, child.parent); 
                        //recoloring
                        child.isBlack=true;
                        if(child.leftChild!=null)
                            child.leftChild.isBlack=false;
                        if(child.rightChild!=null)
                            child.rightChild.isBlack=false;
                    }
                }
                else
                {
                    //Parents sibling is red
                    //recolors
                    child.parent.parent.isBlack=false;
                    if(child.parent.parent.leftChild!=null)
                        child.parent.parent.leftChild.isBlack=true;
                    if(child.parent.parent.rightChild!=null)
                        child.parent.parent.rightChild.isBlack=true;
                    //recursive call up tree for cascading fix
                    enforceRBTreePropertiesAfterInsert(child.parent.parent);
                }
            }
        }
    }

    /** 
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node newNode, Node subtree) {
        int compare = newNode.data.getTitle().compareTo(subtree.data.getTitle());
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

        // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else 
                return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else { 
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node child, Node parent) throws IllegalArgumentException {
        //Right rotation
        if(parent.leftChild!=null && parent.leftChild.equals(child))
        {
            Node grandparent=parent.parent;
            parent.leftChild=child.rightChild;
            //checks if childs right child is null
            if(child.rightChild!=null)
                child.rightChild.parent=parent;
            child.rightChild=parent;
            parent.parent=child;
            child.parent=grandparent;
            //Decides what to do with grandparent and if changing root is neccesary
            if(grandparent!=null)
                if(parent.isLeftChild())
                    grandparent.leftChild=child;
                else
                    grandparent.rightChild=child;
            else 
                root=child;
            return;
        }
        //left rotation
        else if (parent.rightChild!=null && parent.rightChild.equals(child))
        {
            Node grandparent=parent.parent;
            parent.rightChild=child.leftChild;
            //Checks if leftChild of child is null
            if(child.leftChild!=null)
                child.leftChild.parent=parent;
            child.parent=grandparent;
            //Decides what to do with grandparent pointer and if changing root is neccesary
            if(grandparent==null)
                root=child;
            else if (grandparent.leftChild!=null && grandparent.leftChild.equals(parent))
                grandparent.leftChild=child;
            else
                grandparent.rightChild=child;
            child.leftChild=parent;
            parent.parent=child;
            return;
        }
        //If parent and child are not related exception is thrown
        throw new IllegalArgumentException("The parent and child are not related.");
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */

    public int size() {
        return size;
    }

    //Backend Code starts
    /**
     * Finds the book matching a given title
     * @param title the String of the title to be searched for
     * @return the book with the matching title or null if no book is found matching that title
     */
    public Book lookupTitle(String title)
    {
        return lookupTitleHelper(root,title);
    }

    /**
     * Recursive helper for title lookup
     * @param title the String of the title to be searched for
     * @param cur the Node being navigated through
     * @return the book with the matching title or null if no book is found matching that title
     */
    public Book lookupTitleHelper(Node cur,String title)
    {
        if(cur.data.getTitle().equals(title))
            return cur.data;
        else if(cur.rightChild!=null && cur.data.getTitle().compareTo(title)<0)
            return lookupTitleHelper(cur.rightChild,title);
        else if(cur.leftChild!=null && cur.data.getTitle().compareTo(title)>0)
            return lookupTitleHelper(cur.leftChild,title);
        else
            return null;
    }

    /**
     * Finds the books matching a certain given author
     * @param author the String of the author to be searched for
     * @return ArrayList of books matching the author 
     */
    public ArrayList<Book> lookupAuthor(String author)
    {
        ArrayList<Book> list=new ArrayList();
        list=lookupAuthorHelper(author,list,root);
        return list;
    }

    /**
     * Recursive helper for author lookup
     * @param author the String of the author to be searched for
     * @param list the list of books with matching authors
     * @param cur the Node being navigated through
     * @return ArrayList of books matching the author 
     */
    private ArrayList<Book> lookupAuthorHelper(String author, ArrayList<Book> list, Node cur)
    {
        if(cur==null)
            return list;
        lookupAuthorHelper(author,list,cur.leftChild);
        if(cur.data.getAuthor().equals(author))
            list.add(cur.data);
        lookupAuthorHelper(author,list,cur.rightChild);
        return list;
    }

    /**
     * Finds the books matching a certain given author
     * @param author the String of the author to be searched for
     * @return ArrayList of books matching the author 
     */
    public ArrayList<Book> lookupGenre(String genre)
    {
        ArrayList<Book> list=new ArrayList();
        list=lookupGenreHelper(genre,list,root);
        return list;
    }

    /**
     * Recursive helper for author lookup
     * @param author the String of the author to be searched for
     * @param the ArrayList of books that contains all the books matching the genre
     * @param cur the Node being navigated through
     * @return ArrayList of books matching the author 
     */
    private ArrayList<Book> lookupGenreHelper(String genre, ArrayList<Book> list, Node cur)
    {
        if(cur==null)
            return list;
        lookupGenreHelper(genre,list,cur.leftChild);
        if(cur.data.getGenre().equals(genre))
            list.add(cur.data);
        lookupGenreHelper(genre,list,cur.rightChild);
        return list;
    }

    /**
     * Checks in a book with a given title
     * @param title the String that will be searched for to find a book with matching title
     * @throws NoSuchElementException when no book is found matching the title
     * @throws IllegalArgumentException if the book is already checked in
     */

    public void checkIn(String title)
    {
        Book book=lookupTitle(title);
        if(book==null)
            throw new NoSuchElementException("No book matches this title.");
        if(!book.checkedOut())
            throw new IllegalArgumentException("This book is already checked in");
        else if(book.checkedOut())
            book.checkIn();
    }

    /**
     * Checks out a book with a given title
     * @param title the String that will be searched for to find a book with matching title
     * @throws NoSuchElementException when no book is found matching the title
     * @throws IllegalArgumentException if the book is already checked out
     */
    public void checkOut(String title)
    {
        Book book=lookupTitle(title);
        if(book==null)
            throw new NoSuchElementException("No book matches this title.");
        if(book.checkedOut())
            throw new IllegalArgumentException("This book is already checked out");
        else if(!book.checkedOut())
            book.checkOut();
    }

    /**
     * Clears the tree by setting root to null and size back to 0
     */
    public void clear()
    {
        root=null;
        size=0;
    }

    /**
     * Retrieves a list of all books stored in the tree
     * @return an ArrayList of all books in the tree
     */
    public ArrayList<Book> getAllBooks()
    {
        ArrayList<Book> list=new ArrayList();
        list=getAllBooksHelper(list,root);
        return list;
    }

    /**
     * Recursive helper method for getAllBooks
     * @param list the list that the books are being added to
     * @param cur the Node that is being processed
     * @return an ArrayList of all the books in the tree
     */
    private ArrayList<Book> getAllBooksHelper(ArrayList<Book> list, Node cur)
    {
        if(cur==null)
            return list;
        getAllBooksHelper(list,cur.leftChild);
        list.add(cur.data);
        getAllBooksHelper(list,cur.rightChild);
        return list;
    }

    /**
     * Retrieves a list of all genres featured in the tree
     * @return an ArrayList of strings that represent all genres in the tree
     */
    public ArrayList<String> getAllGenres()
    {
        ArrayList<String> list=new ArrayList();
        list=getAllGenresHelper(root,list); 
        return list;
    }

    /**
     * Recursive helper method for getAllGenres
     * @param cur the Node currently being processed
     * @param list the list of Strings representing the genres in the tree
     * @return an ArrayList of strings that represent all genres in the tree
     */
    public ArrayList<String> getAllGenresHelper(Node cur,ArrayList<String> list)
    {
        if(cur==null)
            return list;
        getAllGenresHelper(cur.leftChild,list);
        boolean present=false;
        for(int i=0;i<list.size();i++)
            if(list.get(i).equals(cur.data.getGenre()))
                present=true;
        if(!present)
            list.add(cur.data.getGenre());
        getAllGenresHelper(cur.rightChild,list);
        return list;
    }

    /**
     * Retrieves a list of all authors featured in the tree
     * @return an ArrayList of strings that represent all authors in the tree
     */
    public ArrayList<String> getAllAuthors()
    {
        ArrayList<String> list=new ArrayList();
        list=getAllAuthorsHelper(root,list); 
        return list;
    }

    /**
     * Recursive helper method for getAllAuthors
     * @param cur the Node currently being processed
     * @param list the list of Strings representing the authors in the tree
     * @return an ArrayList of strings that represent all authors in the tree
     */
    public ArrayList<String> getAllAuthorsHelper(Node cur, ArrayList<String> list)
    {
        if(cur==null)
            return list;
        getAllAuthorsHelper(cur.leftChild,list);
        boolean present=false;
        for(int i=0;i<list.size();i++)
            if(list.get(i).equals(cur.data.getAuthor()))
                present=true;
        if(!present)
            list.add(cur.data.getAuthor());
        getAllAuthorsHelper(cur.rightChild,list);
        return list;
    }
}