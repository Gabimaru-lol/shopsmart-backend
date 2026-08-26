package com.arnav.ecommerce.dsa;

import com.arnav.ecommerce.model.Product;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductSorter {

    public static void sort(List<Product> products, Comparator<Product> comparator) {
        if (products.size() <= 1) return;
        mergeSort(products, 0, products.size() - 1, comparator);
    }

    private static void mergeSort(List<Product> list, int left, int right, Comparator<Product> comparator) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(list, left, mid, comparator);
        mergeSort(list, mid + 1, right, comparator);
        merge(list, left, mid, right, comparator);
    }

    private static void merge(List<Product> list, int left, int mid, int right, Comparator<Product> comparator) {
        List<Product> leftHalf = new ArrayList<>(list.subList(left, mid + 1));
        List<Product> rightHalf = new ArrayList<>(list.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftHalf.size() && j < rightHalf.size()) {
            if (comparator.compare(leftHalf.get(i), rightHalf.get(j)) <= 0) {
                list.set(k++, leftHalf.get(i++));
            } else {
                list.set(k++, rightHalf.get(j++));
            }
        }
        while (i < leftHalf.size()) list.set(k++, leftHalf.get(i++));
        while (j < rightHalf.size()) list.set(k++, rightHalf.get(j++));
    }
}