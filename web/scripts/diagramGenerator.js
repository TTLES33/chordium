
//create loading animation while waiting for api response
function loadingPageAnimationPrepare(){
    changeChordLabel();
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

//load chords from api
function loadChordsFromAPI(){
    console.log("Function: loadChordsFromAPI")
    const requestOptions = {
        method: "GET",
        redirect: "follow"
      };
              //TODO: check if numbers if settings are numbers!
      fetch(chordiumSettings.apiUrl + "api/findChordsTransposed?" 
        + new URLSearchParams({
            base: settings.key,
            type: settings.type,
            tuning: settings.tuning.toString().replaceAll(",", ""),
            numberOfStrings: parseInt(settings.numberOfString),
            numberOfFrets: parseInt(settings.numberOfFrets)
        })
        , requestOptions)
        
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            if(result[0]){
                chordDiagramGenerate(result);
            }else{
                throwError(result.message)
            }

           
        })
        .catch(function(error) {                        // catch
            throwError(error.message);
          });
        }

//generate chord SVG from loaded object
function chordDiagramGenerate(chordsArray){
    console.log("Function: chordDiagramGenerate");
 
    //clear all chord diagrams in canvases
    document.getElementById("chordCanvas").innerHTML = "";
    document.getElementById("alternativeChordCanvas").innerHTML = "";


    //generate each chord in chordsArray
    for(i = 0; i < chordsArray.length; i++){

        var chartDiv = document.createElement('div');
        chartDiv.id = "chart" + i;
        chartDiv.className = "chordDiv";

        let frets = []; //frets (fingers) on each string "[string, fretPosition]"
        let tonePositions = chordsArray[i].tonePositions;
        let chordPosition = chordsArray[i].chordPosition;
        let chordWidth = chordsArray[i].chordWidth;
        let barres = chordsArray[i].barres;

        
        //generate frets array
        for(x = 0; x < tonePositions.length; x++){
            let currentFret = tonePositions[x];
            if(currentFret == -1){
                frets.push([x+1, 'x'])
            }else{
                frets.push([x+1, currentFret]); 
            }
        }


        var chart = new svguitar.SVGuitarChord(chartDiv);
        chart.configure(
            {
            frets: chordWidth,
            strings: settings.numberOfString,
            strokeWitdh: 20,
            nutWitdh: 10,
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