var settings = {
    "key":"C",
    "type":"maj",
}
function keyChange(element_id){
    console.log("Function: keyChange");
    console.log("Arguments: " + element_id);
    document.getElementsByClassName("keyButtonSelected")[0].classList.remove("keyButtonSelected");
    document.getElementById(element_id).classList.add("keyButtonSelected");

    settings.key = element_id;
    loadingPageAnimationPrepare();
   loadChordsFromAPI();
}

function typeChange(element_id){
    console.log("Function: typeChange");
    console.log("Arguments: " + element_id);
    if(element_id == "more"){
        openModal();
    }else{
     
        if(element_id.startsWith("modal_")){
            element_id = element_id.substring(6);
        }
        console.log(element_id);
        document.getElementsByClassName("typeButtonSelected")[0].classList.remove("typeButtonSelected");
        document.getElementById(element_id).classList.add("typeButtonSelected");
        settings.type = element_id;



        closeModal();
        loadingPageAnimationPrepare();
        loadChordsFromAPI();
        //chordDiagramGenerate();
    }


}


function loadingPageAnimationPrepare(){
    document.getElementById("chordCanvas").innerHTML = "";
    document.getElementById("alternativeChordCanvas").innerHTML = "";

    var mainChartDiv = document.createElement('div');
    mainChartDiv.className = "chordDiv";
    mainChartDiv.classList.add("fadeInOutAnimation")
    mainChartDiv.style.width = 200;
    mainChartDiv.style.height = 200;
    document.getElementById("chordCanvas").appendChild(mainChartDiv);


    for(i = 0; i < 6; i++){
        let chartDiv = document.createElement('div');
        chartDiv.className = "chordDiv";
        chartDiv.classList.add("fadeInOutAnimation")
        chartDiv.style.width = 200;
        chartDiv.style.height = 200;
        document.getElementById("alternativeChordCanvas").appendChild(chartDiv);
    }

   

}

function loadChordsFromAPI(){
    console.log("Function: loadChordsFromAPI")
    const requestOptions = {
        method: "GET",
        redirect: "follow"
      };
      
      fetch('http://192.168.1.100:8080/api/findChords?' 
        + new URLSearchParams({
            base: settings.key,
            type: settings.type
        })
        , requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            if(result[0]){
                chordDiagramGenerate(result);
            }
           
        })
        .catch((error) => console.error(error));

}

function chordDiagramGenerate(chordsArray){
    console.log("Function: chordDiagramGenerate");
    

    changeChordLabel();
 
    //clear all chord diagrams in canvases
    document.getElementById("chordCanvas").innerHTML = "";
    document.getElementById("alternativeChordCanvas").innerHTML = "";


    //generate each chord in chordsArray
    for(i = 0; i < chordsArray.length; i++){
        console.log(i);
        console.log(chordsArray[i]);

        var chartDiv = document.createElement('div');
        chartDiv.id = "chart" + i;
        chartDiv.className = "chordDiv";

        let frets = []; //frets (fingers) on each string "[string, fretPosition]"
        let chordPosition = chordsArray[i].position;
        let chordsArrayLength =chordsArray[i].tonePositions.length 
        let chordWidth = chordsArray[i].chordWidth;
        let barres = [];

        //generate frets array
        for(x = 0; x < chordsArrayLength; x++){
            //from api string conversely, string 6=1, 5=2, 4=3, 3=4, 2=5, 1=6
            let actualFret = chordsArray[i].tonePositions[chordsArrayLength - 1 - x];
            if(actualFret == -1){
                console.log('x')
                frets.push([x+1, 'x'])
            }else if(actualFret == 0){
                frets.push([x+1, actualFret])
            }else{
                frets.push([x+1, actualFret - chordPosition + 1])
            }
        }

        

        //if chord position == 2, expend chord by 1 fret, so it starts at 1st fret
        if(chordPosition == 2 ){
            chordPosition = 0;
            chordWidth++;
            for(x = 0; x < frets.length; x++){
                if(frets[x][1] != 0 && frets[x][1] != 'x'){
                    frets[x][1] = frets[x][1] + 1;
                }
            }
        }

        //min chord width = 4
        if(chordWidth < 4){
            chordWidth = 4;
        }

        
        console.log(frets);
       
        //generate barres object (+recalculate strings from api)
        if(chordsArray[i].barreEndString != 0 && chordsArray[i].barreStartString != 0){
            barres = [{
                "toString": chordsArrayLength - chordsArray[i].barreEndString,
                "fromString": chordsArrayLength - chordsArray[i].barreStartString,
                "fret": chordsArray[i].barrePosition - chordPosition + 1
            }];
        }
        //console.log(barres);

        var chart = new svguitar.SVGuitarChord(chartDiv);
        chart.configure(
            {
            frets: chordWidth,
            strings: 6,
            //tuning: ['g', 'C', 'E', 'A'],
            //strokeWitdh: 20,
            //nutWitdh: 10,
            color: '#ffffff',
        })
        .chord(
          {
              fingers: frets,
              barres: barres,
              position: chordPosition,
              //title: 
          }
          
        )
        .draw();
        
        if(i == 0){
            document.getElementById("chordCanvas").appendChild(chartDiv);
        }else{
            document.getElementById("alternativeChordCanvas").appendChild(chartDiv);
        }
       
    }
    
}




function closeModal(){
    console.log("Function: closeModal");
    document.getElementById("typeModal").style.display = "none";
}
function openModal(){
    console.log("Function: openModal");
    document.getElementById("typeModal").style.display = "flex";
}

function changeChordLabel(){
    console.log("Function: changeChordLabel");
  //set chord label
    if(settings.type == "maj"){
        document.getElementById("chordName").innerHTML = settings.key;
    }else if(settings.type == "min"){
        document.getElementById("chordName").innerHTML = settings.key + "m"
    }else{
        document.getElementById("chordName").innerHTML = settings.key + settings.type;
    }
}




