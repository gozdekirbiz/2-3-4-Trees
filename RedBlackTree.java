package redblack;
import java.util.ArrayList;
import java.util.List;
class Node {int data;  int renk;Node parent;Node sol;Node sag;}
public class RedBlackTree {
  public Node root;
  private Node TNULL;
  public List < Integer > list = new ArrayList < > ();
  public RedBlackTree() {
    TNULL = new Node();
    TNULL.renk = 0;
    TNULL.sol = null;
    TNULL.sag = null;
    root = TNULL;
  }
  public void AgaciGez(Node node) {
    if (node != TNULL) {
      list.add(node.data);
      AgaciGez(node.sol);
      AgaciGez(node.sag);
    }
  }
  private Node agacAramaRec(Node node, int key) {
    if (node == TNULL) {
      System.out.println("Aranan deger: " + key + " bulunamadi.");
      return node;
    }
    if (key == node.data) {
      System.out.println("Aranan deger: " + node.data + " bulundu.");
      return node;
    }

    if (key < node.data)
      return agacAramaRec(node.sol, key);
    return agacAramaRec(node.sag, key);
  }
  private void fixSilme(Node x) {
    Node s;
    while (x != root && x.renk == 0) //silinecek node root degilse ve rengi siyah oldugu surece
    {
      if (x == x.parent.sol) // silinecek node soldaysa
      {
        s = x.parent.sag; // kardes node sagdaki node olur
        if (s.renk == 1) //kardes nodeun rengi kirmizi ise
        {
          s.renk = 0; //rengini siyah yapariz
          x.parent.renk = 1; // silinecek nodeun babasi kirmizi olur
          leftRotate(x.parent); //lr gerekir
          s = x.parent.sag;
        }
        if (s.sol.renk == 0 && s.sag.renk == 0) // kardesin sol ve sag cocuklari siyahsa
        {
          s.renk = 1; //kardesi kirmizi yap
          x = x.parent; //x, parentina atanir.
        } 
        else {
          if (s.sag.renk == 0) //kardesin sag cocugu siyahsa
          {
            s.sol.renk= 0; //solu siyah yapar
            s.renk = 1; //kardesi kirmizi yapar
            rightRotate(s);
            s = x.parent.sag;
          }
          s.renk = x.parent.renk; //kardesin rengi, silinecek nodeun parentinin rengi olur
          x.parent.renk = 0; //silinecek nodeun parenti siyah yapilir
          s.sag.renk = 0; //kardesin sag cocugu siyah yapilir
          leftRotate(x.parent);
          x = root; //silinecek nodea root degeri atanir.
        }
      } 
     else // silinecek node sagdaysa
      {
        s = x.parent.sol; // kardesi soldaki node olur
        if (s.renk == 1) //kardesin rengi kirmizi ise
        {
          s.renk = 0; //siyah yapilir
          x.parent.renk = 1; //silinecek nodeun rengi kirmizi yapilir
          rightRotate(x.parent);
          s = x.parent.sol;
        }
        if (s.sol.renk == 0 && s.sag.renk == 0) //kardesin cocuklarinin rengi siyahsa
        {
          s.renk = 1; //kardes kirmizi yapilir
          x = x.parent;
        } 
        else {
          if (s.sol.renk == 0) //kardesin sol cocugu siyahsa
          {
            s.sag.renk = 0; //sag cocuk da siyah yapilir
            s.renk = 1; //kardes kirmizi yapilir.
            leftRotate(s);
            s = x.parent.sol;
          }
          s.renk = x.parent.renk;
          x.parent.renk = 0;
          s.sol.renk = 0;
          rightRotate(x.parent);
          x = root;
        }
      }
    }
    x.renk = 0;
  }
  private void rbTransplant(Node u, Node v) {
    if (u.parent == null) //u root ise
      root = v; //root degerini v yap
    else if (u == u.parent.sol) // u parentin sol cocugu ise
      u.parent.sol = v; //v ye bu degeri aktar
    else
      u.parent.sag= v;
    v.parent = u.parent; //vnin parentini u nun parenti yap
  }
  private void deleteNodeHelper(Node node, int key) {
    Node z = TNULL;
    Node x, y;
    while (node != TNULL) {
      if (node.data == key)
        z = node;
      if (node.data <= key)
        node = node.sag;
      else
        node = node.sol;
    }
    if (z == TNULL) {
      System.out.println("Bu deger agacta bulunmamaktadir.");
      return;
    }
    y = z;
    int yOriginalColor = y.renk; //nodeun orjinal rengini bir degiskende tutuyoruz
    if (z.sol == TNULL) { //nodeun solu bossa, sagi doludur
      x = z.sag; // x degerine nodeun sag degerini aktar
      rbTransplant(z, z.sag); //node degeri ile sag degerini yer degistir
    } else if (z.sag == TNULL) {
      x = z.sol;
      rbTransplant(z, z.sol);
    } else // nodeun her iki tarafi da doluysa
    {
      y = minimum(z.sag); //nodeun sagindaki degerden baslayarak surekli sola gidip min degeri bul
      yOriginalColor = y.renk; //orjinal rengi en kucuk nodeun rengi ile degistir
      x = y.sag; //x nodeunda, minimum nodeun sagindaki degeri tut
      if (y.parent == z) // en kucuk nodeun parenti ilk nodeun kendisi ise
        x.parent = y; // en kucuk nodeun sagindaki nodeun parentini y yap
      else {
        rbTransplant(y, y.sag);
        y.sag = z.sag;
        y.sag.parent = y;
      }
      rbTransplant(z, y);
      y.sol = z.sol;
      y.sol.parent = y;
      y.renk = z.renk;
    }
    if (yOriginalColor == 0) //eger orjinal renk siyahsa fixlenmesi gerekir
      fixSilme(x);
  }
  private void fixInsert(Node k) {
    Node u;
    while (k.parent.renk== 1) //dugumun parenti kirmiziysa
    {
      if (k.parent == k.parent.parent.sag) //parent uncledan buyukse yani sagdaysa
      {
        u = k.parent.parent.sol; //u nodeuna kucuk olan unclei ata
        if (u.renk == 1) // uncle kirmizisiysa
        {
          u.renk = 0; // uncle rengini siyah yap
          k.parent.renk = 0; // parent nodeu da siyah yap
          k.parent.parent.renk = 1; // dedeyi kirmizi yap
          k = k.parent.parent; // nodeu dede yap
        } 
        else //uncle siyahsa
        {
          if (k == k.parent.sol) // k nodeu kucuk olansa, yani soldaysa
          {
            k = k.parent; //k nodeunu parent yap
            rightRotate(k); //right rotate gerekiyor
          }
          k.parent.renk = 0; //babayi siyah yap
          k.parent.parent.renk = 1; //dedeyi kirmizi yap
          leftRotate(k.parent.parent); // left rotate gerekiyor.
        }
      } 
      else // parent, uncledan kucukse yani soldaysa
      {
        u = k.parent.parent.sag; //uncle nodeu dedenin saginda olacak
        if (u.renk == 1) // eger uncle kirmizi ise
        {
          u.renk = 0; //uncle nodeunu siyah yap
          k.parent.renk = 0; //babayi siyah yap
          k.parent.parent.renk = 1; //dedeyi kirmizi yap
          k = k.parent.parent;
        } 
        else //uncle siyah ise
        {
          if (k == k.parent.sag) // eger k nodeu buyuk olansa,yani sagdaysa
          {
            k = k.parent; //k nodeunu parent yap
            leftRotate(k); // lr gerekiyor
          }
          k.parent.renk = 0; //babayi siyah yap
          k.parent.parent.renk = 1; // dedeyi kirmizi yap
          rightRotate(k.parent.parent);
        }
      }
      if (k == root) //eger k degeri root ise duzeltmeye gerek yok
        break;
    }
    root.renk = 0; // tum islemlerin sonucunda root siyah kalmalidir.
  }
  private void printHelper(Node root, String i, boolean last) {
    if (root != TNULL) {
      System.out.print(i);
      if (last) {
        System.out.print("R---- ");
        i += "   ";
      } else {
        System.out.print("L---- ");
        i += "|  ";
      }
      String renk = root.renk == 1 ? "RED" : "BLACK";
      System.out.println(root.data + "(" + renk + ")");
      printHelper(root.sol, i, false);
      printHelper(root.sag, i, true);
    }
  }
  public Node minimum(Node node) {
    while (node.sol != TNULL)
      node = node.sol;
    return node;
  }
  public Node maximum(Node node) {
    while (node.sag != TNULL)
      node = node.sag;
    return node;
  }
  public void leftRotate(Node x) {
    Node y = x.sag;
    x.sag = y.sol;
    if (y.sol != TNULL)
      y.sol.parent = x;
    y.parent = x.parent;
    if (x.parent == null)
      this.root = y;
    else if (x == x.parent.sol)
      x.parent.sol = y;
    else
      x.parent.sag = y;
    y.sol= x;
    x.parent = y;
  }
  public void rightRotate(Node x) {
    Node y = x.sol;
    x.sol = y.sag;
    if (y.sag != TNULL)
      y.sag.parent = x;
    y.parent = x.parent;
    if (x.parent == null)
      this.root = y;
    else if (x == x.parent.sag)
      x.parent.sag = y;
    else
      x.parent.sol = y;
    y.sag = x;
    x.parent = y;
  }
  public void insert(int key) {
    Node node = new Node();
    node.parent = null;
    node.data = key;  	node.renk = 1;
    node.sol = TNULL;	node.sag = TNULL;
    Node y = null;	Node x = this.root;
    while (x != TNULL) //root bos degilse
    {
      y = x; //yeni node'a deger aktar
      if (node.data < x.data) //eklenen dugum roottan kucukse sola aktar
        x = x.sol;
      else //eklenen dugum roottan buyukse sagina aktar
        x = x.sag;
    }
    node.parent = y; //bu nodeun parentina root degerini ata.
    if (y == null) //root yoksa bu nodeu root yap.
      root = node;
    else if (node.data < y.data) // bu dugumun degeri rootun degerinden buyukse soluna
      y.sol = node;
    else //kucukse sagina ata.
      y.sag = node;
    if (node.parent == null) //eger parenti yoksa dugumun rengini siyah yap
    {
      node.renk = 0;
      return;
    }
    if (node.parent.parent == null) //ikinci dereceden bir parent yoksa fixlemeye gerek yoktur
      return;
    fixInsert(node);
  }
  public void deleteNode(int data) {deleteNodeHelper(this.root, data);}
  public void printTree() {printHelper(this.root, "", true);}
  public Node agacArama(int k) {return agacAramaRec(this.root, k);}
}