Laborationsuppgift 1 - Java-Programmering

Dela in din applikation i lämpliga metoder och tänk på namngivning av variabler och
metoder. För VG som betyg på uppgiften behöver man göra uppgift 5, inläsning från fil.
Inlämning av uppgiften sker enklast som länk till ett git repo. Det är då enkelt att pusha nya
commits om det saknas något i uppgiften för godkänt.
Uppgift:

1. Meny
Börja med att skapa ett konsol program med hjälp av Java som vid start skriver ut en meny med
följande utseende:
Elpriser
========
1. Inmatning
2. Min, Max och Medel
3. Sortera
4. Bästa Laddningstid (4h)
e. Avsluta

När alternativ e väljs ska programmet avslutas. Både e och E ska vara giltiga som val för att avsluta.
Vid val av något av de övriga alternativen ska dessa köras och när körningen är klar ska menyn åter
skrivas ut på skärmen så att ett nytt val kan göras.


1. Inmatning
Senaste åren har elpriserna blivit högre och varierar mycket. Det här programmet ska kunna hjälpa
till med att analysera elpriser för ett dygn. När man väljer alternativet inmatning från menyn ska
programmet fråga efter priserna under dygnets timmar. Inmatningen av värden ska ske med hela
ören. Tex kan priser vara 50 102 eller 680 öre per kWh. Priset sätts per intervall mellan två hela
timmar. Dygnets första pris är då mellan 00-01, andra intervallet är mellan 01-02 osv.


2. Min Max Medel
När alternativ 2 väljs på menyn så ska programmet skriva ut lägsta priset, högsta priset samt vilka
timmar som detta infaller under dygnet. Dygnets medelpris ska också räknas fram och presenteras
på skärmen.


3. Sortering
Skriv ut timmarna och priset för dessa sorterade efter billigast till dyrast pris. Ex:
00-01 23 öre
01-02 26 öre
05-06 30 öre
02-03 40 öre
   
4. Bästa laddningstid (4 h)
Om man har en elbil som man vill ladda så vill man kanske göra det när priset är som billigast på dygnet. Då
batteriet behöver värmas/kylas så blir det minst förluster om man genomför laddningen sammanhängande
under ett antal timmar. Låt programmet hitta de 4 billigaste timmarna som ligger i följd och skriva ut vid
vilket klockslag man ska börja ladda för att få lägst totalpris samt vilket medelpris det blir under dessa 4
timmar.


5. Inläsning från fil, CSV (VG uppgift)
Den här uppgiften är för VG och kommer att involvera att man själv söker information om hur man på bästa
sätt i modern java läser från en textfil. Det innebär att man kommer behöva göra en del efterforskning och
inte bara välja första bästa lösning som presenteras av en sökmotor eller chatbot.
För att slippa mata in informationen manuellt kan vi istället låta programmet läsa in en lista med priserna
från en kommaseparerad textfil kallad en CSV-fil. Implementera ett menyalternativ som kan läsa in priser
från en fil som heter priser.csv och ligger i programmets aktuella arbetskatalog.
https://en.wikipedia.org/wiki/Comma-separated_values
