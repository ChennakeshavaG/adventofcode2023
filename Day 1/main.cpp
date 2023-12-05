#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
   fstream inputFile;
   string tp;
   char first,last;
   int result = 0;
   inputFile.open("input.txt",ios::in);
   if (inputFile.is_open()){
      while(getline(inputFile, tp)){
         for(int i=0;i<tp.size();i++){
            char c = tp.at(i);
            if(c >= 48 && c<=57) {
                if(first == NULL){
                    first = c;
                    last = c;
                } else {
                    last = c;
                }
            }
         }
         string resultString;
         resultString += first;
         resultString += last;
         first = NULL;
         last = NULL;
         result += stoi(resultString);
         cout << resultString << endl;
      }
      inputFile.close();
   }
   cout << "Result = " << result;
}
