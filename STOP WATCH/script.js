let hr=0;
let min=0;
let sec=0;
let count=0;
let timer=false; //this variable will decide that whether the timer is running or not and false denotes that the timer is stopped and when it will be true then the timmer is running


function start(){
    timer=true;
    stopWatch();
}
function stop(){
    timer=false;
}
function reset(){
    timer=false;
    hr=0;
    min=0;
    sec=0;
    count=0;
    //to display the changes when we will press reset button
    document.getElementById("hr").innerHTML="00"; //we have done this cause in maths 00==0 
    document.getElementById("min").innerHTML="00";
    document.getElementById("sec").innerHTML="00";
    document.getElementById("count").innerHTML="00";
}
//the main logic lies in this function
function stopWatch(){
    if(timer == true){
        count=count+1;
        if(count==100){
            sec=sec+1;
            count=0;
        }
        //for second
        if(sec==60){
            min=min+1;
            sec=0;
        }
        //for minute
        if(min==60){
            hr=hr+1;
            min=0;
            sec=0;
        }
        var hrString=hr;
        var minString=min;
        var secString=sec;
        var countString=count;
        //for hour we will append 0 if it is less than 10. 
        if(hr<10){
            hrString="0"+hrString;
        }
        if(min<10){
            minString="0"+minString;
        }
        if(sec<10){
            secString="0"+secString;
        }
        if(count<10){
            countString="0"+countString;
        }
        



        
        document.getElementById("hr").innerHTML=hrString;
        document.getElementById("min").innerHTML=minString;
        document.getElementById("sec").innerHTML=secString; //it will reflect the value of sec on the webpage
        document.getElementById("count").innerHTML=countString; //it will reflect the value of count on the webpage
        setTimeout("stopWatch()",10); //here 10 cause we know that 1 sec=1000 millisec.And the count part we have to go till 0-99
    }
}