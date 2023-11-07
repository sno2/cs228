package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node. Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 * 
 * @author Carter Snook
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;

  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;

  /**
   * Dummy node for head. It should be private but set to public here only
   * for grading purpose. In practice, you should always make the head of a
   * linked list a private instance variable.
   */
  public Node head;

  /**
   * Dummy node for tail.
   */
  private Node tail;

  /**
   * Number of elements in the list.
   */
  private int size;

  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList() {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * 
   * @param nodeSize number of elements that may be stored in each node, must be
   *                 an even number
   */
  public StoutList(int nodeSize) {
    if (nodeSize <= 0 || nodeSize % 2 != 0)
      throw new IllegalArgumentException();

    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }

  /**
   * Constructor for grading only. Fully implemented.
   * 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size) {
    this.head = head;
    this.tail = tail;
    this.nodeSize = nodeSize;
    this.size = size;
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * A helper method for easily adding nodes to the list.
   * 
   * @param theChosenOne the node to be added
   * @param before       the node before theChosenOne
   * @param after        the node after theChosenOne
   */
  private void link(Node theChosenOne, Node before, Node after) {
    before.next = theChosenOne;
    after.previous = theChosenOne;
    theChosenOne.previous = before;
    theChosenOne.next = after;
  }

  /**
   * A helper method for easily removing nodes from the list.
   * 
   * @param theChosenOne the node to be removed
   */
  private void unlink(Node theChosenOne) {
    theChosenOne.previous.next = theChosenOne.next;
    theChosenOne.next.previous = theChosenOne.previous;
  }

  @Override
  public boolean add(E item) {
    if (item == null) {
      throw new NullPointerException();
    }

    if (tail.previous != head && tail.previous.count < nodeSize) {
      tail.previous.addItem(item);
    } else {
      Node newNode = new Node();
      newNode.addItem(item);
      link(newNode, tail.previous, tail);
    }

    size++;

    return true;
  }

  /**
   * Adds an item to the list at the given position. Requires the node to
   * already be within the list with a non-zero count.
   * 
   * @param n      the node to add to
   * @param offset the offset to add at within the node
   * @param item   the item to add
   * @return information about the node that was added to
   */
  private NodeInfo addWithInfo(Node n, int offset, E item) {
    // if the list is empty, create a new node and put X at offset 0
    if (isEmpty()) {
      Node node = new Node();
      node.addItem(item);
      link(node, head, tail);
      return new NodeInfo(node, 0);
    }

    // otherwise if off = 0 and one of the following two cases occurs:
    // NOTE: We early return earlier so that we don't have as much nesting.

    // if n has a predecessor which has fewer than M elements (and is not the
    // head), put X in n's predecessor
    if (offset == 0 && n.previous != head && n.previous.count < nodeSize) {
      n.previous.addItem(item);
      return new NodeInfo(n.previous, n.previous.count - 1);
    }

    // if n is the tail node and n’s predecessor has M elements, create a new
    // node and put X at offset 0
    else if (n == tail && n.previous.count == nodeSize) {
      Node newNode = new Node();
      newNode.addItem(item);
      link(newNode, tail.previous, tail);
      return new NodeInfo(newNode, 0);
    }

    else {
      // otherwise if there is space in node n, put X in node n at offset off,
      // shifting array elements as necessary
      if (n.count < nodeSize) {
        n.addItem(offset, item);
        return new NodeInfo(n, offset);
      }

      // otherwise, perform a split operation: move the last M/2 elements of node
      // n into a new successor node n', and then
      else {
        Node successor = new Node();
        link(successor, n, n.next);

        // split operation: move the last M/2 elements of node n into a new
        // successor
        for (int i = 0; i < nodeSize / 2; i++) {
          successor.data[i] = n.data[i + nodeSize / 2];
          n.data[i + nodeSize / 2] = null;
        }

        successor.count = n.count = nodeSize / 2;

        // if off <= M/2, put X in node n at offset off, shifting array elements
        if (offset <= nodeSize / 2) {
          n.addItem(offset, item);
          return new NodeInfo(n, offset);
        }

        // otherwise, put X in node n' at offset off - M/2, shifting array elements
        else {
          successor.addItem(offset - nodeSize / 2, item);
          return new NodeInfo(successor, offset - nodeSize / 2);
        }
      }
    }
  }

  @Override
  public void add(int pos, E item) {
    if (item == null) {
      throw new NullPointerException();
    }

    NodeInfo info = find(pos);
    addWithInfo(info.node, info.offset, item);

    size++;
  }

  @Override
  public E remove(int pos) {
    NodeInfo info = find(pos);
    E value;

    // if the node n containing X is the last node and has only one element,
    // delete it
    if (info.node.next == tail && info.node.count == 1) {
      value = info.node.data[0];
      unlink(info.node);
    }

    // otherwise, if n is the last node (thus with two or more elements), or
    // if n has more than M/2 elements, remove X from n, shifting elements as
    // necessary
    else if (info.node.next == tail || info.node.count > nodeSize / 2) {
      value = info.node.data[info.offset];
      info.node.removeItem(info.offset);
    }

    // otherwise (the node n must have at most M/2 elements), look at its successor
    // n' (note that we don’t look at the predecessor of n) and perform a merge
    // operation as follows:
    else {
      Node successor = info.node.next;
      value = info.node.data[info.offset];
      info.node.removeItem(info.offset);

      // if the successor node n' has more than M/2 elements, move the first
      // element from n' to n. (mini-merge)
      if (successor.count > nodeSize / 2) {
        info.node.addItem(successor.data[0]);
        successor.removeItem(0);
      }

      // if the successor node n' has M/2 or fewer elements, then move all
      // elements from n' to n and delete n' (full merge)
      else {
        for (int i = 0; i < successor.count; i++) {
          info.node.addItem(successor.data[i]);
        }

        unlink(successor);
      }
    }

    size--;

    return value;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do
   * the following.
   * Traverse the list and copy its elements into an array, deleting every visited
   * node along
   * the way. Then, sort the array by calling the insertionSort() method. (Note
   * that sorting
   * efficiency is not a concern for this project.) Finally, copy all elements
   * from the array
   * back to the stout list, creating new nodes for storage. After sorting, all
   * nodes but
   * (possibly) the last one must be full of elements.
   * 
   * Comparator<E> must have been implemented for calling insertionSort().
   */
  public void sort() {
    E[] data = (E[]) new Comparable[size];
    int pos = 0;

    for (Node cur = head.next; cur != tail; cur = cur.next) {
      for (int i = 0; i < cur.count; i++) {
        data[pos++] = cur.data[i];
      }
      unlink(cur);
    }

    insertionSort(data, Comparator.naturalOrder());

    for (int i = 0; i < data.length; i += nodeSize) {
      Node node = new Node();
      for (int j = 0; j < nodeSize && i + j < data.length; j++) {
        node.addItem(data[i + j]);
      }
      link(node, tail.previous, tail);
    }
  }

  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the
   * bubbleSort()
   * method. After sorting, all but (possibly) the last nodes must be filled with
   * elements.
   * 
   * Comparable<? super E> must be implemented for calling bubbleSort().
   */
  public void sortReverse() {
    E[] data = (E[]) new Comparable[size];
    int pos = 0;

    for (Node cur = head.next; cur != tail; cur = cur.next) {
      for (int i = 0; i < cur.count; i++) {
        data[pos++] = cur.data[i];
      }
      unlink(cur);
    }

    bubbleSort(data);

    for (int i = 0; i < data.length; i += nodeSize) {
      Node node = new Node();
      for (int j = 0; j < nodeSize && i + j < data.length; j++) {
        node.addItem(data[i + j]);
      }
      link(node, tail.previous, tail);
    }
  }

  @Override
  public Iterator<E> iterator() {
    return new StoutIterator();
  }

  @Override
  public ListIterator<E> listIterator() {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return new StoutListIterator(index);
  }

  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal() {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) {
    int count = 0;
    int position = -1;
    if (iter != null) {
      position = iter.nextIndex();
    }

    StringBuilder sb = new StringBuilder();
    sb.append('[');
    Node current = head.next;
    while (current != tail) {
      sb.append('(');
      E data = current.data[0];
      if (data == null) {
        sb.append("-");
      } else {
        if (position == count) {
          sb.append("| ");
          position = -1;
        }
        sb.append(data.toString());
        ++count;
      }

      for (int i = 1; i < nodeSize; ++i) {
        sb.append(", ");
        data = current.data[i];
        if (data == null) {
          sb.append("-");
        } else {
          if (position == count) {
            sb.append("| ");
            position = -1;
          }
          sb.append(data.toString());
          ++count;

          // iterator at end
          if (position == size && count == size) {
            sb.append(" |");
            position = -1;
          }
        }
      }
      sb.append(')');
      current = current.next;
      if (current != tail)
        sb.append(", ");
    }
    sb.append("]");
    return sb.toString();
  }

  /**
   * Node type for this list. Each node holds a maximum
   * of nodeSize elements in an array. Empty slots
   * are null.
   */
  private class Node {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];

    /**
     * Link to next node.
     */
    public Node next;

    /**
     * Link to previous node;
     */
    public Node previous;

    /**
     * Index of the next available offset in this node, also
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * 
     * @param item element to be added
     */
    void addItem(E item) {
      if (count >= nodeSize) {
        return;
      }
      data[count] = item;
      count++;
    }

    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * 
     * @param offset array index at which to put the new element
     * @param item   element to be added
     */
    void addItem(int offset, E item) {
      if (count >= nodeSize) {
        return;
      }
      for (int i = count - 1; i >= offset; --i) {
        data[i + 1] = data[i];
      }
      count++;
      data[offset] = item;
    }

    /**
     * Deletes an element from this node at the indicated offset,
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * 
     * @param offset
     */
    void removeItem(int offset) {
      for (int i = offset + 1; i < nodeSize; ++i) {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      count--;
    }
  }

  /**
   * Returns the node and offset of the element at the given position in the
   * list.
   * 
   * @param pos position of the element in the list
   */
  private NodeInfo find(int pos) {
    int currentOffset = 0;
    Node current = head.next;

    // find the node containing the element at the given offset
    for (; current != tail && pos >= currentOffset + current.count; current = current.next) {
      currentOffset += current.count;
    }

    // find the offset of the element within the node
    return new NodeInfo(current, pos - currentOffset);
  }

  private class NodeInfo {
    /**
     * Node containing the element at the given offset.
     */
    public Node node;

    /**
     * Offset of the element in the node.
     */
    public int offset;

    public NodeInfo(Node node, int offset) {
      this.node = node;
      this.offset = offset;
    }
  }

  private class StoutIterator implements Iterator<E> {
    Node current;
    int currentOffset;

    public StoutIterator() {
      current = head.next;
      currentOffset = 0;
    }

    @Override
    public boolean hasNext() {
      if (current == tail) {
        return false;
      } else if (current.next == tail) {
        return currentOffset < current.count;
      } else {
        return true;
      }
    }

    @Override
    public E next() {
      if (current == tail || (current == tail.previous && currentOffset >= current.count)) {
        throw new IllegalStateException();
      }

      E item = current.data[currentOffset];
      currentOffset++;

      if (currentOffset == nodeSize) {
        current = current.next;
        currentOffset = 0;
      }

      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Iterator for this list.
   */
  private class StoutListIterator implements ListIterator<E> {
    /**
     * Node containing the element at the given offset.
     */
    Node current;

    /**
     * The cursor within the node.
     */
    int offset;

    /**
     * The last state. May be null.
     */
    StateInfo lastState;

    /**
     * Whether the iterator is in a valid state for certain modification
     * methods.
     */
    boolean validModifiers = false;

    class StateInfo {
      Node node;
      int offset;

      StateInfo(Node node, int offset) {
        this.node = node;
        this.offset = offset;
      }
    }

    public StoutListIterator() {
      current = head.next;
      offset = 0;
      validModifiers = false;
    }

    public StoutListIterator(int pos) {
      if (pos == 0) {
        current = head.next;
        offset = 0;
      } else if (pos == size()) {
        current = tail.previous;
        offset = tail.previous.count;
      } else {
        NodeInfo info = find(pos);
        current = info.node;
        offset = info.offset;

        saveState();
      }

      validModifiers = false;
    }

    /**
     * Saves the current state of the iterator.
     */
    private void saveState() {
      lastState = new StateInfo(current, offset);
    }

    /**
     * Returns the current index in the iterator.
     */
    private int getCurrentIndex() {
      int index = offset;

      for (Node n = current.previous; n != head; n = n.previous) {
        index += n.count;
      }

      return index;
    }

    @Override
    public boolean hasNext() {
      return getCurrentIndex() < size;
    }

    @Override
    public int nextIndex() {
      return getCurrentIndex();
    }

    @Override
    public boolean hasPrevious() {
      return getCurrentIndex() > 0;
    }

    @Override
    public int previousIndex() {
      return getCurrentIndex() - 1;
    }

    @Override
    public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      validModifiers = true;
      saveState();

      E item = current.data[offset];
      offset++;

      if (offset == current.count && current.next != tail) {
        current = current.next;
        offset = 0;
      }

      return item;
    }

    @Override
    public E previous() {
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }

      validModifiers = true;
      offset--;

      if (offset < 0) {
        current = current.previous;
        offset = current.count - 1;
      }

      saveState();
      E item = current.data[offset];

      return item;
    }

    @Override
    public void remove() {
      if (!validModifiers) {
        throw new IllegalStateException();
      }

      NodeInfo info = find(getCurrentIndex() - 1);

      // if the node n containing X is the last node and has only one element,
      // delete it
      if (info.node.next == tail && info.node.count == 1) {
        unlink(info.node);
      }

      // otherwise, if n is the last node (thus with two or more elements), or
      // if n has more than M/2 elements, remove X from n, shifting elements as
      // necessary
      else if (info.node.next == tail || info.node.count > nodeSize / 2) {
        info.node.removeItem(info.offset);
      }

      // otherwise (the node n must have at most M/2 elements), look at its successor
      // n' (note that we don’t look at the predecessor of n) and perform a merge
      // operation as follows:
      else {
        Node successor = info.node.next;
        info.node.removeItem(info.offset);

        // if the successor node n' has more than M/2 elements, move the first
        // element from n' to n. (mini-merge)
        if (successor.count > nodeSize / 2) {
          info.node.addItem(successor.data[0]);
          successor.removeItem(0);
        }

        // if the successor node n' has M/2 or fewer elements, then move all
        // elements from n' to n and delete n' (full merge)
        else {
          for (int i = 0; i < successor.count; i++) {
            info.node.addItem(successor.data[i]);
          }

          unlink(successor);
        }
      }

      size--;

    }

    @Override
    public void set(E e) {
      if (!validModifiers) {
        throw new IllegalStateException();
      }

      lastState.node.data[lastState.offset] = e;
    }

    @Override
    public void add(E e) {
      if (e == null) {
        throw new NullPointerException();
      }

      if (lastState == null) {
        saveState();

        Node node = new Node();
        node.addItem(e);
        link(node, head, tail);
        current = node;
        offset = 0;
      } else {
        NodeInfo info = addWithInfo(lastState.node, lastState.offset, e);
        current = info.node;
        offset = info.offset;
      }

      size++;
    }

  }

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
   * order.
   * 
   * @param arr  array storing elements from the list
   * @param comp comparator used in sorting
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp) {
    for (int i = 1; i < arr.length; i++) {
      for (int j = i; j > 0 && comp.compare(arr[j], arr[j - 1]) < 0; j--) {
        E temp = arr[j];
        arr[j] = arr[j - 1];
        arr[j - 1] = temp;
      }
    }
  }

  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
   * description of bubble sort please refer to Section 6.1 in the project
   * description.
   * You must use the compareTo() method from an implementation of the Comparable
   * interface by the class E or ? super E.
   * 
   * @param arr array holding elements from the list
   */
  private void bubbleSort(E[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = arr.length - 1; j > i; j--) {
        if (arr[j].compareTo(arr[j - 1]) > 0) {
          E temp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = temp;
        }
      }
    }
  }

}