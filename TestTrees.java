package redblack;
import java.util.Scanner;
public class TestTrees {
  public static void main(String[] args) {
    RedBlackTree rbt = new RedBlackTree();
    char secim = '.';
    int deger;
    System.out.println("Red-Black Agaci Islem Menusu");
    do {
      System.out.println("E: Ekle\t S:Sil\t B:Bul\t G:Goster C:Cikis");
      Scanner sc = new Scanner(System.in);
      secim = sc.next().charAt(0);
      Scanner s = new Scanner(System.in);
      if (secim == 'E' || secim == 'e') {
        System.out.print("Eklenecek degeri giriniz:");
        deger = s.nextInt();
        rbt.insert(deger);
      }
      if (secim == 'S' || secim == 's') {
        System.out.print("Silinecek degeri giriniz:");
        deger = s.nextInt();
        rbt.deleteNode(deger);
      }
      if (secim == 'B' || secim == 'b') {
        System.out.print("Aranacak degeri giriniz:");
        deger = s.nextInt();
        rbt.agacArama(deger);
      }
      if (secim == 'G' || secim == 'g')
        rbt.printTree();
      if (secim == 'C' || secim == 'c')
        break;
    } while (secim != '.');
    System.out.println("\n\n 2-3-4 Agaci:");
    Tree234 tree = new Tree234();
    rbt.AgaciGez(rbt.root);
    for (int i = 0; i < rbt.list.size(); i++)
      tree.insert(rbt.list.get(i));
    tree.displayTree();
  }
}