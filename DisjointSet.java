/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author Raji
 */
public class DisjointSet{
    int[] s;
    public DisjointSet(int elements){
        s=new int[elements];
        for(int i=0;i<s.length;i++){
            s[i]=-1;
        }
    }

    public void union(int r1, int r2){
        if(s[r2]<s[r1]){
            s[r2]+=s[r1];
            s[r1]=r2;
        }else{
            s[r1]+=s[r2];
            s[r2]=r1;
        }
    }

    public int find(int a){
        if(s[a]<0){
            return a;
        }else{
            return s[a]=find(s[a]);
        }
    }

    public String toString(){
        String res = "";
        for(int i=0;i<s.length;i++){
            res+="\t"+s[i];
        }
        return res;
    }
}
