var chords = {
    "C":{
        "maj":[
            {
                "fingers":[[1,3]],
                "barres":[],
                "frets": 3,
                "position":1
            },
            {
                "fingers":[[3,2],[4,3]],
                "barres":[
                    {
                        "fromString":2,
                        "toString":1,
                        "fret":1
                    }
                ],
                "frets": 3,
                "position":3
            },
            {
                "fingers":[[1,3],[2,4],[3,3],[4,1]],
                "barres":[],
                "frets": 4,
                "position":5
            }
        ],
        "min":[
            {
                "fingers":[[1,3],[2,3],[3,3]],
                "barres":[],
                "frets": 3,
                "position":1
            },
            {
                "fingers":[[4,3]],
                "barres":[
                    {
                        "fromString":4,
                        "toString":1,
                        "fret":1
                    }
                ],
                "frets": 3,
                "position":3
            },
            {
                "fingers":[[1,2],[2,4],[3,3],[4,1]],
                "barres":[],
                "frets": 4,
                "position":5
            }, 
            {
                "fingers":[[1,1],[2,3],[3,2],[4,3]],
                "barres":[],
                "frets": 3,
                "position":6
            },
            {
                "fingers":[[1,4],[2,2],[3,1],[4,2]],
                "barres":[],
                "frets": 4,
                "position":7
            }
        ],
        "aug":[
            {
                "fingers":[[1,3],[4,1]],
                "barres":[],
                "frets": 3,
                "position":1
            },
            {
                "fingers":[[1,1],[2,2],[3,2],[4,3]],
                "barres":[],
                "frets": 3,
                "position":3
            },
            {
                "fingers":[[1,4],[4,2]],
                "barres":[
                    {
                        "fromString":4,
                        "toString":1,
                        "fret":1
                    }
                ],
                "frets": 4,
                "position":4
            },
            {
                "fingers":[[1,3],[2,4],[3,4],[4,1]],
                "barres":[],
                "frets": 4,
                "position":5
            }
        ],
        "dim":[
            {
                "fingers":[[1,3],[2,2],[3,3],[4, "x"]],
                "barres":[],
                "frets": 3,
                "position":1
            },
            {
                "fingers":[[1,3],[2,2],[3,3],[4,5]],
                "barres":[],
                "frets": 5,
                "position":1
            },
            {
                "fingers":[[1,2],[3,2],[4,1]],
                "barres":[],
                "frets": 3,
                "position":5
            },   
            {
                "fingers":[[1,2],[4,2]],
                "barres":[
                    {
                        "fromString":4,
                        "toString":1,
                        "fret":1
                    }
                ],
                "frets": 3,
                "position":6
            },
            {
                "fingers":[[1,2],[2,1],[4,1]],
                "barres":[],
                "frets": 3,
                "position":8
            }
        ],
        
        "7":[
            {
                "fingers":[[1,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[3,1]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":3
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,3],[2,2],[3,3],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,1],[2,2],[3,4]],
               "barres":[{
              
              }],
              "frets":4,
              "position":7
              } 
        ],
        "m7":[
            {
                "fingers":[],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,1],[2,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":6
              },
              {
                "fingers":[[1,1],[2,4],[3,1]],
               "barres":[{
              
              }],
              "frets":4,
              "position":3
              },
              {
                "fingers":[[1,2],[2,2],[3,3],[4,4]],
               "barres":[{
              
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,3],[3,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":6
              }
              
              
        ], 
        "maj7":[
            {
                "fingers":[[1,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,3],[2,3],[3,4],[4,4]],
               "barres":[{
              
              }],
              "frets":4,
              "position":1
              },
              {
                "fingers":[[0,1],[1,2],[2,3],[3,4],[4,5]],
               "barres":[{
              
              }],
              "frets":5,
              "position":1
              },
              {
                "fingers":[[1,3],[2,3],[3,3],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":5
              }  
        ],
        "aug7":[
            {
                "fingers":[[1,1],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[2,1],[3,1]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":1
              }  
        ],
        "dim7":[
            {
                "fingers":[[1,3],[2,2],[3,3],[4,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[2,2],[3,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              }
              
        ],
        "sus2":[
            {
                "fingers":[[1,3],[2,3],[3,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,3],[2,3],[3,2],[4,5]],
               "barres":[{
              
              }],
              "frets":5,
              "position":1
              },
              {
                "fingers":[[2,3],[3,2]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":4,
              "position":5
              }
        ],
        "sus4":[
            {
                "fingers":[[1,3],[2,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,1],[2,1],[3,3],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,4],[2,4],[3,3],[4,1]],
               "barres":[{
              
              }],
              "frets":4,
              "position":5
              }
              
              
        ],
        "7sus2":[
            {
                "fingers":[[2,3],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },          
            {
                "fingers":[[2,2],[3,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,1],[2,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,3],[2,3],[3,2],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              }
              
        ],
        "7sus4":[
            {
                "fingers":[[1,1],[2,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[3,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,4],[2,2],[3,3],[4,1]],
               "barres":[{
              
              }],
              "frets":4,
              "position":5
              },
              {
                "fingers":[[1,3],[2,1],[3,3],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":8
              }   
        ],
        "9":[
            {
                "fingers":[[1,3],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,2],[2,3],[3,1],[4,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":4
              },
              {
                "fingers":[[1,3],[3,2],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[2,2],[3,4]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":4,
              "position":7
              }
              
        ],
        "maj9":[
            {
                "fingers":[[1,3],[4,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,3],[3,2],[4,4]],
               "barres":[{
              
              }],
              "frets":4,
              "position":1
              },
              {
                "fingers":[[1,2],[3,2],[4,5]],
               "barres":[{
              
              }],
              "frets":5,
              "position":1
              },
              {
                "fingers":[[1,2],[2,4],[3,1],[4,2]],
               "barres":[{
              
              }],
              "frets":4,
              "position":4
              },
              {
                "fingers":[[1,2],[2,5]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":5,
              "position":4
              },
              {
                "fingers":[[1,1],[2,1],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":7
              }
              
        ],
        "11":[
            {
                "fingers":[[1,1],[3,5],[4,5]],
               "barres":[{
              
              }],
              "frets":5,
              "position":1
              },
              {
                "fingers":[[1,3],[2,2]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,1],[3,3],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[3,4],[4,5]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":5,
              "position":1
              },
              {
                "fingers":[[3,3],[4,2]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":8
              },
              {
                "fingers":[[1,1],[2,2],[3,4],[4,4]],
               "barres":[{
              
              }],
              "frets":4,
              "position":7
              }
              
        ],
        "m11":[
            {
                "fingers":[[1,3],[2,1],[3,3],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,2],[2,2],[3,1],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[3,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":8
              }
        
        ],
        "13":[
            {
                "fingers":[[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,1],[4,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,1],[2,3],[3,2],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              }
                      
        ],
        "m13":[
            {
                "fingers":[[2,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":3
              }  
        ],
        "6":[
            {
                "fingers":[],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,2],[2,2],[3,3],[4,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":2
              },
              {
                "fingers":[[2,1],[3,2],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,1],[2,3],[3,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,3],[3,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,3],[2,1],[3,2],[4,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":8
              }          
        ],
        "m6":[
            {
                "fingers":[[1,3],[2,3],[3,3],[4,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,1],[2,3],[3,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[1,2],[3,3]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":5
              },
              {
                "fingers":[[1,3],[3,2]],
               "barres":[{
              "fromString":4,
              "toString":1,
              "fret":1
              }],
              "frets":3,
              "position":8
              }
              
        ],
        "add9":[
            {
                "fingers":[[1,3],[3,2]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              }
              
        ],
        "m9":[
            {
                "fingers":[[1,3],[2,4],[3,1],[4,3]],
               "barres":[{
              
              }],
              "frets":4,
              "position":3
              },
              {
                "fingers":[[1,1],[2,2],[4,4]],
               "barres":[{
              
              }],
              "frets":4,
              "position":5
              }
              
        ],
        "5":[
            {
                "fingers":[[1,3],[2,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":1
              },
              {
                "fingers":[[1,1],[2,1],[4,3]],
               "barres":[{
              
              }],
              "frets":3,
              "position":3
              },
              {
                "fingers":[[2,2],[3,1]],
               "barres":[{
              
              }],
              "frets":3,
              "position":7
              }
              
        ]
    
    },
    "D":{
        "maj":[
           
        ],
        "min":[
          
        ],
        "aug":[
           
        ],
        "dim":[
       
        ],
        
        "7":[
           
        ],
        "m7":[
            
        ], 
        "maj7":[
    
        ],
        "aug7":[
            
        ],
        "dim7":[
            
        ],
        "sus2":[
            
        ],
        "sus4":[
            
        ],
        "7sus2":[
            
        ],
        "7sus4":[
            
        ],
        "9":[
            
        ],
        "maj9":[
            
        ],
        "11":[
            
        ],
        "m11":[
            
        ],
        "13":[
            
        ],
        "m13":[
            
        ],
        "6":[
            
        ],
        "m6":[
            
        ],
        "add9":[
            
        ],
        "m9":[
            
        ],
        "5":[
            
        ]
    
    }
    }