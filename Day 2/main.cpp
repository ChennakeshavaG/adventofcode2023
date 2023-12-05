#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <map>
#include <sstream>
using namespace std;


int main()
{
   fstream inputFile;
   string input;
   char first,last;
   int redMax = 12;
   int greenMax = 13;
   int blueMax = 14;
   int result = 0;
   int game = 1;
   inputFile.open("input.txt",ios::in);
   if (inputFile.is_open()){
        while(getline(inputFile, input)){
            vector<string> gameSet;
            input = input.substr(input.find(":") + 1);

            stringstream ss (input);
            while (ss.good()) {
                string substr;
                getline(ss, substr, ';');
                gameSet.push_back(substr);
            }
            bool flag = true;
            for (string set : gameSet){
                stringstream sss(set);
                vector<string> subSet;
                while (sss.good()) {
                    string substr;
                    getline(sss, substr, ',');
                    subSet.push_back(substr);
                }
                for (string color : subSet){
                    int colorCount;
                    string colorChar;
                    for (int i=0;i<color.size();i++){       
                        if(color.at(i) >= 48 && color.at(i) <=57) {
                            colorChar +=  color.at(i);
                        }
                    }
                    colorCount = stoi(colorChar);
                    if(color.find("red") < 10 && colorCount > redMax){
                        flag = false;
                        break;
                    } else if (color.find("green") < 10 && colorCount > greenMax){
                        flag = false;
                        break;
                    } else if (color.find("blue") < 10 && colorCount > blueMax){
                        flag = false;
                        break;
                    }
                }
            }
            if (flag){
                result += game;
            }
            game++;
        }
        inputFile.close();
        cout <<"Result" << result <<endl;
   }
}