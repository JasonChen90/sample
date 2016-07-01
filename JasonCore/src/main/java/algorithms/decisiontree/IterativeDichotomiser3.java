package algorithms.decisiontree;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class IterativeDichotomiser3 {

	/**
	 * ������ֵ
	 * @param dataSet ���ݼ�
	 * @return ��ֵ
	 */
	public float CalcEntropy(ArrayList<Object[]> dataSet){
		try {
			int len = dataSet.size();
			Map<Object, Integer> k = new HashMap<>();
			for (Object[] objects : dataSet) {
				Object o = objects[objects.length - 1];
				if(!k.keySet().contains(o)){
					k.put(o, 0);
				}
				k.put(o, k.get(o) + 1);
			}
			float e = 0;
			for (Entry<Object, Integer> d : k.entrySet()) {
				float p = (float)d.getValue()/len;
				e -= p * (Math.log(p)/Math.log(2));	
			}
			return e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * ������ݼ�
	 * @param dataset ���ݼ�
	 * @param axis ����������
	 * @param value ����ֵ
	 * @return �������Ե���value�������ݼ�
	 */
	public ArrayList<Object[]> SplitDataSet(ArrayList<Object[]> dataset, int axis, Object value){
		ArrayList<Object[]> r = new ArrayList<>();
		for (Object[] objects : dataset) {
			if(objects[axis].equals(value)){
				Object[] s = new Object[objects.length - 1];
				for (int i = 0,j = 0; i < objects.length; i++) {
					if(i!=axis){
						s[j] = objects[i];
						j++;
					}
				}
				r.add(s);
			}
		}
		return r;
	}
	
	/**
	 * ѡ����ֵ��С����Ϣ�����������Խ������ݼ����
	 * ��⣺ĳһ�еĿ���ֵԽ���Ҷ�Ӧ�Ľ�����Խ�ࡢ(���и��ʼ��㼴��ֵ����Խ��)˵�����в�ȷ���Խϴ�
	 * ��ȷ����Խ��Խ������Ϊ�ؼ�����ȥ���ߡ�Ӧ�÷ŵ����²�
	 * ��ֵ���㹫ʽ��g(x)=-P(x)logP(x) (��Ϊ2��P(X) <= 1) Ϊ������������
	 * @param dataset
	 * @return
	 */
	public int ChooseBestFeatureToSplit(ArrayList<Object[]> dataset){
		float baseE= CalcEntropy(dataset);
		int bestF = -1;
		float bestIG = 0;
		//�����и���
		int featureCount = dataset.get(0).length;
		int len = dataset.size();
		for (int i = 0; i < featureCount - 1; i++) {
			//��ȡĳһ���ԵĲ�ֵͬ����ֵͬ�ĸ���
			Map<Object,Integer> featureValue = new HashMap<>();
			for (Object[] objects : dataset) {
				if(!featureValue.containsKey(objects[i])){
					featureValue.put(objects[i], 0);
				}
				featureValue.put(objects[i], featureValue.get(objects[i]) + 1);
			}
			float featureE = 0;
			for (Entry<Object,Integer> fv : featureValue.entrySet()) {
				float tempE = CalcEntropy(SplitDataSet(dataset, i, fv.getKey()));
				//ĳһ����ֵ�ĸ��ʳ��Ը�ֵ����
				featureE +=  tempE * ((float)fv.getValue()/len);	
			}
			//(���������ԣ���ȷ���ԣ��Ķ���ָ��)
			//��Ϣ��(Information Gain)Խ�󡢼����Ե���ֵԽС���������Ե�Ӱ��Ĳ�ȷ����ԽС�����Ը����ܹ�ȷ��ֵ
			//���Ծ����������ϲ�ڵ�Ӧ������Ϣ�����ֵ��
			float tempIg = baseE - featureE;
			if(tempIg > bestIG){
				bestIG = tempIg;
				bestF = i;
			}
		}
		return bestF;
	}
	
	/**
	 * ���������
	 * @param dataset ���ݼ�
	 * @param labels ���Լ��ϼ�ֵ��
	 * @param parentNode ���ڵ�
	 * @return
	 */
	public Node<String> CreateTree(ArrayList<Object[]> dataset, Map<Integer, String> labels, Node<String> parentNode){
		ArrayList<Object> classifiedTarget = new ArrayList<>();
		for (Object[] objects : dataset) {
			classifiedTarget.add(objects[objects.length - 1]);
		}
		//���ĳ�������ݼ���Ŀ������һ�����򷵻�
		if(IsCompletedSame(classifiedTarget)){
			Node<String> node = new Node<String>(parentNode, classifiedTarget.get(0).toString(), null);
			return node;
		}
		//������������Զ������ꡢ��ѡ���������Ե�ֵ������
		if(dataset.get(0).length == 1){
			Map<Object, Integer> classCount = new HashMap<>();
			for (Object object : classifiedTarget) {
				if(!classCount.containsKey(object)) classCount.put(object, 0);
				classCount.put(object, classCount.get(object) + 1);
			}
			Entry<Object, Integer> m = null;
			Integer t = 0;
			for (Entry<Object, Integer> s : classCount.entrySet()) {
				if(s.getValue() > t){
					t = s.getValue();
					m = s;
				}
			}
			return new Node<String>(parentNode, m.getKey().toString(), null);
		}
		int bestF = ChooseBestFeatureToSplit(dataset);
		String label = labels.get(bestF);
		Node<String> node = new Node<String>(parentNode == null ? null : parentNode, label, null);
		node.child = new ArrayList<>();
		labels.remove(bestF);
		Set<Object> featureValues = GetFeatureValues(dataset,bestF).keySet();
		for (Object object : featureValues) {
			Node<String> child = CreateTree(SplitDataSet(dataset, bestF, object), labels, node);
			node.child.add(child);
		}
		return node;
	}
	
	/**
	 * �ж�ArrayList��������ֵ��һ��
	 * @param al
	 * @return
	 */
	private boolean IsCompletedSame(ArrayList<Object> al){
		if(al == null || al.size() <= 1) return true;
		Object c = al.get(0);
		Iterator<Object> iterator = al.iterator();
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			if(!object.equals(c)) return false;
		}
		return true;
	}
	
	/**
	 * ��ȡĳһ�������п���ֵ��ͬһֵ�ĸ���
	 * @param dataset ���ݼ�
	 * @param axis ����������ָ����
	 * @return
	 */
	private Map<Object,Integer> GetFeatureValues(ArrayList<Object[]> dataset, int axis){
		ArrayList<Object> classifiedTarget = new ArrayList<>();
		for (Object[] objects : dataset) {
			classifiedTarget.add(objects[axis]);
		}
		//��ȡĳһ���ԵĲ�ֵͬ����ֵͬ�ĸ���
		Map<Object, Integer> featureValue = new HashMap<>();
		for (Object objects : classifiedTarget) {
			if(!featureValue.containsKey(objects)){
				featureValue.put(objects, 0);
			}
			featureValue.put(objects, featureValue.get(objects) + 1);
		}
		return featureValue;
	}
	/**
	 * �������ڵ�
	 * @author JasonChen1
	 *
	 * @param <E>
	 */
	private class Node<E> {
        E item;
        java.util.List<Node<E>> child;
        Node<E> parent;

        Node(Node<E> parent, E element, java.util.List<Node<E>> child) {
            this.item = element;
            this.child = child;
            this.parent = parent;
        }
    }
	
	@Test
	public void TestM3(){
		String[] property1 = {"sunny","hot","high","FALSE","no"};
		String[] property2 = {"sunny","hot","high","TRUE","no"};
		String[] property3 = {"overcast","hot","high","FALSE","yes"};
		String[] property4 = {"rainy","mild","high","FALSE","yes"};
		String[] property5 = {"rainy","cool","normal","FALSE","yes"};
		String[] property6 = {"rainy","cool","normal","TRUE","no"};
		String[] property7 = {"overcast","cool","normal","TRUE","yes"};
		String[] property8 = {"sunny","mild","high","FALSE","no"};
		String[] property9 = {"sunny","cool","normal","FALSE","yes"};
		String[] property10 = {"rainy","mild","normal","FALSE","yes"};
		String[] property11 = {"sunny","mild","normal","TRUE","yes"};
		String[] property12 = {"overcast","mild","high","TRUE","yes"};
		String[] property13 = {"overcast","hot","normal","FALSE","yes"};
		String[] property14 = {"rainy","mild","high","TRUE","no"};
		
		
		ArrayList<Object[]> dataset = new ArrayList<Object[]>();
		dataset.add(property1);
		dataset.add(property2);
		dataset.add(property3);
		dataset.add(property4);
		dataset.add(property5);
		dataset.add(property6);
		dataset.add(property7);
		dataset.add(property8);
		dataset.add(property9);
		dataset.add(property10);
		dataset.add(property11);
		dataset.add(property12);
		dataset.add(property13);
		dataset.add(property14);
		Map<Integer, String> labels = new HashMap<>();
		labels.put(0, "outlook");
		labels.put(1, "temperature");
		labels.put(2, "humidity");
		labels.put(3, "windy");
		labels.put(4, "play");
		Node<String> decisionTree = CreateTree(dataset, labels, null);
		System.out.println(decisionTree);
	}
	

	
	@Test
	public void TestM(){
		String[] property1 = {"1","1","yes"};
		String[] property2 = {"1","1","yes"};
		String[] property3 = {"1","0","no"};
		String[] property4 = {"0","1","no"};
		String[] property5 = {"0","1","no"};
		
		
		ArrayList<Object[]> dataset = new ArrayList<Object[]>();
		dataset.add(property1);
		dataset.add(property2);
		dataset.add(property3);
		dataset.add(property4);
		dataset.add(property5);
		float e = CalcEntropy(dataset);
		System.out.println(e);
		ArrayList<Object[]> subDataSet1 = SplitDataSet(dataset, 0, "1");
		e = CalcEntropy(subDataSet1);
		System.out.println(e);
		ArrayList<Object[]> subDataSet2 = SplitDataSet(dataset, 0, "0");
		e = CalcEntropy(subDataSet2);
		System.out.println(e);
		int bestfeature = ChooseBestFeatureToSplit(dataset);
		System.out.println(bestfeature);
	}
	
	@Test
	public void TestM2(){
		String[] property1 = {"sunny","hot","high","FALSE","no"};
		String[] property2 = {"sunny","hot","high","TRUE","no"};
		String[] property3 = {"overcast","hot","high","FALSE","yes"};
		String[] property4 = {"rainy","mild","high","FALSE","yes"};
		String[] property5 = {"rainy","cool","normal","FALSE","yes"};
		String[] property6 = {"rainy","cool","normal","TRUE","no"};
		String[] property7 = {"overcast","cool","normal","TRUE","yes"};
		String[] property8 = {"sunny","mild","high","FALSE","no"};
		String[] property9 = {"sunny","cool","normal","FALSE","yes"};
		String[] property10 = {"rainy","mild","normal","FALSE","yes"};
		String[] property11 = {"sunny","mild","normal","TRUE","yes"};
		String[] property12 = {"overcast","mild","high","TRUE","yes"};
		String[] property13 = {"overcast","hot","normal","FALSE","yes"};
		String[] property14 = {"rainy","mild","high","TRUE","no"};
		
		
		ArrayList<Object[]> dataset = new ArrayList<Object[]>();
		dataset.add(property1);
		dataset.add(property2);
		dataset.add(property3);
		dataset.add(property4);
		dataset.add(property5);
		dataset.add(property6);
		dataset.add(property7);
		dataset.add(property8);
		dataset.add(property9);
		dataset.add(property10);
		dataset.add(property11);
		dataset.add(property12);
		dataset.add(property13);
		dataset.add(property14);
		int bestfeature = ChooseBestFeatureToSplit(dataset);
		System.out.println(bestfeature);
	}
	
	@Test
	public void TestNg(){
		ArrayList<Object> test = new ArrayList<>();
		test.add(1);
		test.add(1);
		test.add(2);
	}
}
