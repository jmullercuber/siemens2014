/* 
 * Copyright 2014 Joey.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

load(arguments[0])

fileName = arguments[0];
fileName = fileName.split("\\")[fileName.split("\\").length-1];
fileName = fileName.split("/")[fileName.split("/").length-1];
fileName = fileName.split(".")[fileName.split(".").length-2];

print("public interface Intrf" + fileName);
for (i in this) {
    print(i + "\t type:" + (typeof this[i]));
    if (this[i] instanceof Function) {
	returnType = typeof (this[i]());
	returnType = (returnType=="undefined")?"void":returnType;
        print("    public " + returnType +" " + i + "();");
    }
}