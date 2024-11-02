var settings = {
    key:"C",
    type:"maj",
    numberOfString: 6,
    numberOfFrets: 15,
    tuning: ["E", "A", "D", "G", "B", "E"]
}

const predefinedinstruments = {
    "guitar": {
        numberOfStrings: 6,
        numberOfFrets: 15,
        tunings:[
            ["E", "A", "D", "G", "B", "E"],
            ["D", "A", "D", "G", "B", "E"],
            ["E", "A", "D", "F#", "A", "D"],
            ["C", "G", "C", "F", "A", "D"],
        ]
    },
    "ukulele":{
        numberOfStrings: 4,
        numberOfFrets: 10,
        tunings:[
            ["G", "C", "E", "A"],
            ["E", "A", "D", "G"],
            ["E", "A", "D", "G"],
            ["D", "G", "B", "E"],
        ]
    }
}
const tones = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]


const colors = [
    "#A22522",
    "#4E4187",
    "#A49966",
    "#2E5266",
    "#2C6E49",
    "#775B59",
    "#0F7173",
    "#5867ff",
    "#A4243B",
    "#60463B",
    "#087F8C",
    "#095256",
    "#8F3985",
    "#248232",
    "#931621",
    "#2B4570",
    "#2dd4be",
]


function colorsShow(){
    let container = document.getElementById("colors");
    for(i = 0; i < colors.length; i++){
        let button = document.createElement("button");
        button.id = "color_" + i;
        button.innerHTML = colors[i];
        button.onclick = function(){
            document.documentElement.style.setProperty('--highlightColor', colors[this.id.split("_")[1]]);
        }
        container.appendChild(button);
    }
    
}


//change chord key 
function keyChange(element_id){
    console.log("Function: keyChange");
    console.log("Arguments: " + element_id);
    document.getElementsByClassName("keyButtonSelected")[0].classList.remove("keyButtonSelected");
    document.getElementById(element_id).classList.add("keyButtonSelected");

    settings.key = element_id;
    loadingPageAnimationPrepare();
   loadChordsFromAPI();
}

//change chord type 
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


function closeModal(){
    console.log("Function: closeModal");
    document.getElementById("typeModal").style.display = "none";
}
function openModal(){
    console.log("Function: openModal");
    document.getElementById("typeModal").style.display = "flex";
}




//open dialog for more options
function openMoreSettings(){
    //delete instrument container
    let instrumentSelector = document.getElementById("instrumentSelector");
    instrumentSelector.innerHTML = "";


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
        numberOfStringInput.value = settings.numberOfString;
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
        stringInput.max
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

//change tuning (IN MORE OPTIONS DIALOGE)
function changeTuning(changed_element){
    let element_id = changed_element.id;
    let splittedArray = element_id.split("_");
    let tuningId = splittedArray[1];
    settings.tuning[tuningId] = changed_element.value;
}


//change pre defined instrument
function instrumentChange(element){
    console.log("instrumentChange");

    let instrument = element.id.split("_")[1];
    document.getElementsByClassName("instrument-selected")[0].classList.remove("instrument-selected");
    element.classList.add("instrument-selected");

    settings.numberOfString = predefinedinstruments[instrument].numberOfStrings;
    settings.numberOfFrets = predefinedinstruments[instrument].numberOfFrets;
    settings.tuning = predefinedinstruments[instrument].tunings[0];

    tuningsGenerate(instrument);
}


//generate buttons with predefined tunings
function tuningsGenerate(instrument){
    console.log("tuningsGenerate");

    let instrumentTunings =  document.getElementById("instrumentTunings");
    instrumentTunings.innerHTML = "";
  
    let instrumentSetting = predefinedinstruments[instrument];
    for(i = 0; i < instrumentSetting.tunings.length; i++){
        let tuningButton = document.createElement("div");

        //id
        tuningButton.id = "tuningButton_" + i;

        //classes
        tuningButton.classList.add("tuning", "selectorButton");
        if(i === 0){
            tuningButton.classList.add("tuningSelected");
        }

        //onclick
        tuningButton.onclick = function(){
            instrumentTuningChange(this, instrument);
        }


        //innerText
        let tuningArray = instrumentSetting.tunings[i];
        console.log(tuningArray);
        let tuningString = "";
        for(x = 0; x < tuningArray.length; x++){
            tuningString = tuningString + "-" + tuningArray[x];
        }
        tuningString = tuningString.substring(1,tuningString.length);
        tuningButton.innerText = tuningString;



        instrumentTunings.appendChild(tuningButton);

    }
    loadChordsFromAPI();
}

function instrumentTuningChange(element, instrument){

    let tuning_id = element.id.split("_")[1];
    document.getElementsByClassName("tuningSelected")[0].classList.remove("tuningSelected");
    element.classList.add("tuningSelected");

    settings.tuning = predefinedinstruments[instrument].tunings[tuning_id];

    loadChordsFromAPI();
}

