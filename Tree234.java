package redblack;
class DataItem {
  public double dData;
  public DataItem(double dd) {
    dData = dd;
  }
  public void displayItem() {
    System.out.print("/" + dData);
  }
}
class Node1 {
  private static final int ORDER = 4;
  private int numItems;
  private Node1 parent;
  private Node1 childArray[] = new Node1[ORDER];
  private DataItem itemArray[] = new DataItem[ORDER - 1];
  public void connectChild(int childNum, Node1 child) {
    childArray[childNum] = child;
    if (child != null)
      child.parent = this;
  }
  public Node1 disconnectChild(int childNum) {
    Node1 tempNode = childArray[childNum];
    childArray[childNum] = null;
    return tempNode;
  }
  public Node1 getChild(int childNum) {
    return childArray[childNum];
  }
  public Node1 getParent() {
    return parent;
  }
  public boolean isLeaf() {
    return (childArray[0] == null) ? true : false;
  }
  public int getNumItems() {
    return numItems;
  }
  public DataItem getItem(int index) {
    return itemArray[index];
  }
  public boolean isFull() {
    return (numItems == ORDER - 1) ? true : false;
  }
  public DataItem removeItem() {
    DataItem temp = itemArray[numItems - 1];
    itemArray[numItems - 1] = null;
    numItems--;
    return temp;
  }
  public int insertItem(DataItem newItem) {
    numItems++;
    double newKey = newItem.dData;
    for (int j = ORDER - 2; j >= 0; j--) {
      if (itemArray[j] == null)
        continue;
      else {
        double itsKey = itemArray[j].dData;
        if (newKey < itsKey)
          itemArray[j + 1] = itemArray[j];
        else {
          itemArray[j + 1] = newItem;
          return j + 1;
        }
      }
    }
    itemArray[0] = newItem;
    return 0;
  }
  public void displayNode() {
    for (int j = 0; j < numItems; j++)
      itemArray[j].displayItem();
    System.out.println("/");
  }
}
public class Tree234 {
  private Node1 root = new Node1();
  public void insert(double dValue) {
    Node1 curNode = root;
    DataItem tempItem = new DataItem(dValue);
    while (true) {
      if (curNode.isFull()) {
        split(curNode);
        curNode = curNode.getParent();
        curNode = getNextChild(curNode, dValue);
      } else if (curNode.isLeaf())
        break;
      else
        curNode = getNextChild(curNode, dValue);
    }
    curNode.insertItem(tempItem);
  }
  public void split(Node1 curNode) {
    DataItem itemB, itemC;
    Node1 parent, child2, child3;
    int itemIndex;

    itemC = curNode.removeItem();
    itemB = curNode.removeItem();
    child2 = curNode.disconnectChild(2);
    child3 = curNode.disconnectChild(3);
    Node1 newRight = new Node1();
    if (curNode == root) {
      root = new Node1();
      parent = root;
      root.connectChild(0, curNode);
    } else
      parent = curNode.getParent();
    itemIndex = parent.insertItem(itemB);
    int n = parent.getNumItems();
    for (int j = n - 1; j > itemIndex; j--) {
      Node1 temp = parent.disconnectChild(j);
      parent.connectChild(j + 1, temp);
    }
    parent.connectChild(itemIndex + 1, newRight);
    newRight.insertItem(itemC);
    newRight.connectChild(0, child2);
    newRight.connectChild(1, child3);
  }
  public Node1 getNextChild(Node1 theNode, double theValue) {
    int j;
    int numItems = theNode.getNumItems();
    for (j = 0; j < numItems; j++) {
      if (theValue < theNode.getItem(j).dData)
        return theNode.getChild(j);
    }
    return theNode.getChild(j);
  }
  public void displayTree() {
    recDisplayTree(root, 0, 0);
  }
  private void recDisplayTree(Node1 thisNode, int level, int childNumber) {
    System.out.print("Level=" + level + " Child=" + childNumber + " ");
    thisNode.displayNode();
    int numItems = thisNode.getNumItems();
    for (int j = 0; j < numItems + 1; j++) {
      Node1 nextNode = thisNode.getChild(j);
      if (nextNode != null)
        recDisplayTree(nextNode, level + 1, j);
      else
        return;
    }
  }
}
