# jandan

# DroidData
## Structure
```
Model -> 

ModelDao -> 
  * query
  * insert
  * update
  * delete
  
ModelTrans <- Model
  -> ModelObservable

ContentProvider <- ModelDao._allchanges
  -> insert
  -> update
  -> delete
  
  -> query -> Dao.query -> Cursor -> Model
```
  

  
