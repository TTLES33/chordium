var settings = {
    key:"C",
    type:"maj",
    numberOfString: 6,
    numberOfFrets: 15,
    tuning: ["E", "A", "D", "G", "B", "E"]
}
const tones = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
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

function loadChordsFromAPI(){
    console.log("Function: loadChordsFromAPI")
    const requestOptions = {
        method: "GET",
        redirect: "follow"
      };
              //TODO: check if numbers if settings are numbers!
      fetch('http://192.168.1.100:8080/api/findChordsTransposed?' 
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
            }
           
        })
        .catch((error) => console.error(error));

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


//open dialog for more options
function openMoreSettings(){

    //get container element
    let container = document.getElementById("moreSettingsContainer");
        container.innerHTML = "";
    
    //create grid element for options
    let formGrid = document.createElement("div");
    formGrid.className = "moreSettingGrid";

    //number of strings
    let numberOfStringText = document.createElement("div");
        numberOfStringText.innerHTML = "Number of strings:";
        numberOfStringText.className = "grid-item";
    formGrid.appendChild(numberOfStringText);

    let numberOfStringInput = document.createElement("input");
        numberOfStringInput.type = "number";
        numberOfStringInput.value = "6";
        numberOfStringInput.className = "grid-item-value";
        numberOfStringInput.oninput = function(){
            changeNumberOfStrings(this);
        }
        formGrid.appendChild(numberOfStringInput);


    //number of frets
    let numberOfFretText = document.createElement("div");
        numberOfFretText.innerHTML = "Number of frets:";
        numberOfFretText.className = "grid-item";
        formGrid.appendChild(numberOfFretText);

    let numberOfFretInput = document.createElement("input");
        numberOfFretInput.type = "number";
        numberOfFretInput.value = settings.numberOfFrets;
        numberOfFretInput.className = "grid-item-value";
        numberOfFretInput.oninput = function(){
            changeNumberOfFrets(this);
        }
        formGrid.appendChild(numberOfFretInput);


    //custom tuning
    let customTuning = document.createElement("div");
        customTuning.innerHTML = "String tuning:";
        customTuning.className = "grid-item";
        formGrid.appendChild(customTuning);

    let customTuningContainer = document.createElement("div");
        customTuningContainer.id = "customTuningContainer";
        customTuningContainer.className = "customTuningContainer";
        formGrid.appendChild(customTuningContainer);

    
    let searchButton = document.createElement("input");
        searchButton.classList.add("searchbutton");
        searchButton.type = "button";
        searchButton.onclick = function(){ 
            loadingPageAnimationPrepare();
            loadChordsFromAPI();
        };
        searchButton.value = "search"

    formGrid.appendChild(searchButton);
    container.appendChild(formGrid);
   

    
    //create custom tuning dialog
    openCustomTuning();
}



//called by changing value in number of strings input
function changeNumberOfStrings(changed_element){
    //check if entered value is a number
    let newValue = changed_element.value;
    if(newValue.length !== 0){
        //edit value in settings
        settings.numberOfString = parseInt(newValue);

        //add / remove values to tuning array
        if(settings.tuning.length < newValue){
            let biggerBy = newValue - settings.tuning.length;
            for(i = 0; i < biggerBy; i++){
                settings.tuning.push("C");
            }
        }else{
            let smallerBy = settings.tuning.length - newValue;
            settings.tuning.splice(newValue - 1, smallerBy);
        }

        settings.tuning.length = newValue;

        //regenerate tuning dialog
        openCustomTuning();

    }
}

//called by changing value in number of frets input
function changeNumberOfFrets(changed_element){
    //check if entered value is a number
    let newValue = changed_element.value;
    if(newValue.length !== 0){
        //edit value in settings
        settings.numberOfFrets = parseInt(newValue);
    }
}

//create custom tuning dialog
function openCustomTuning(){
    //get container
    let customTuningContainer = document.getElementById("customTuningContainer");
    customTuningContainer.innerHTML = "";
   
    //loop through all strings
    let numberOfStrings = settings.numberOfString;
    for(i = 0; i < numberOfStrings; i++){
        let stringContainer = document.createElement("div");
        stringContainer.className = "custom-tuning-string-container";

        let text = document.createElement("div");
        text.innerHTML = i+1;

        let stringInput = document.createElement("select");
        stringInput.type = "number";
        stringInput.id = "stringInput_" + i;
        stringInput.oninput = function(){
            changeTuning(this);
        }

        for(x = 0; x < tones.length; x++){
            let option = document.createElement("option");
            option.value = tones[x];
            option.text = tones[x];
            //check if option is set in settings
            if(settings.tuning[i] == tones[x]){
                console.log(true);
                option.selected = true;
            }

            stringInput.appendChild(option);
        }
        stringContainer.appendChild(text);
        stringContainer.appendChild(stringInput);
        
        customTuningContainer.appendChild(stringContainer);
        
    }
}


function changeTuning(changed_element){
    let element_id = changed_element.id;
    let splittedArray = element_id.split("_");
    let tuningId = splittedArray[1];
    settings.tuning[tuningId] = changed_element.value;
}


