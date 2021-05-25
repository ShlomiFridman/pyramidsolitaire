package application;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class CardList implements List<Card>,Iterable<Card>,Cloneable {
	
	LinkedList<Card> cards;
	ObservableList<Node> nodes;
	
	
	public CardList(ObservableList<Node> nodes) {
		this.nodes=nodes;
		this.cards = new LinkedList<>();
	}

	public CardList(ObservableList<Node> nodes,List<Card> list) {
		this(nodes);
		addAll(list);
	}
	
	/**
	 * Return at the last card without removing it
	 * @return Card
	 */
	public Card peek() { 
		return cards.peekLast();
	}

	/**
	 * Removes and return the last card
	 * @return Card
	 */
	public Card remove() {
		nodes.remove(nodes.size()-1);
		return cards.removeLast();
	}

	@Override
	public Card remove(int index) {
		nodes.remove(index);
		return cards.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		try {
			cards.remove((Card) o);
			nodes.remove(((Card) o).getLabel());
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Return and remove from given index to the end
	 * @return List<Card>
	 */
	public List<Card> removeFrom(int index){
		List<Card> res = new LinkedList<>();
		while (size()>index)
			res.add(remove(index));
		return res;
	}
	
	/**
	 * Return and remove all items;
	 */
	public List<Card> removeAll(){
		List<Card> res = new LinkedList<>();
		while (size()>0)
			res.add(remove(0));
		return res;
	}

	@Override
	public int size() {
		if (cards.size()!=nodes.size()) return -1;
		return cards.size();
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return cards.contains(o);
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	@Override
	public Object[] toArray() {
		return cards.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return cards.toArray(a);
	}

	@Override
	public boolean add(Card card) {
		try {
			if (nodes.contains(card.getLabel()))
					return true;
			cards.add(card);
			nodes.add(card.getLabel());
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return cards.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Card> c) {
		if (!c.isEmpty() && nodes.contains(c.toArray()[0]))
			return true;
		return cards.addAll(c) && nodes.addAll(toLabelList(c));
	}

	@Override
	public boolean addAll(int index, Collection<? extends Card> c) {
		return cards.addAll(index,c) && nodes.addAll(index,toLabelList(c));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean removeAll(Collection<?> c) {
		return cards.removeAll(c) && cards.removeAll(toLabelList(c));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean retainAll(Collection<?> c) {
		return cards.retainAll(c) && cards.retainAll(toLabelList(c));
	}

	@Override
	public void clear() {
		cards.clear();
		nodes.clear();
	}

	@Override
	public Card get(int index) {
		nodes.get(index);
		return cards.get(index);
	}

	@Override
	public Card set(int index, Card element) {
		nodes.set(index, element.getLabel());
		return cards.set(index, element);
	}

	@Override
	public void add(int index, Card element) {
		cards.add(index,element);
		nodes.add(index, element.getLabel());
	}

	@Override
	public int indexOf(Object o) {
		return cards.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return cards.lastIndexOf(o);
	}

	@Override
	public ListIterator<Card> listIterator() {
		return cards.listIterator();
	}

	@Override
	public ListIterator<Card> listIterator(int index) {
		return cards.listIterator(index);
	}

	@Override
	public List<Card> subList(int fromIndex, int toIndex) {
		return cards.subList(fromIndex, toIndex);
	}
	
	@Override
	public List<Card> clone(){
		return cards.subList(0, cards.size());
	}
	
	private List<Label> toLabelList(Collection<?> c){
		List<Label> list = new LinkedList<>();
		Iterator<?> it = c.iterator();
		while (it.hasNext())
			list.add(((Card) it.next()).getLabel());
		return list;
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}

}
