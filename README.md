# Jandan.app
## Modules
domain

data

app

# DroidData
## Model Structure
```
Model
  * must have id column

ModelObservable <- ModelDao._allchanges
  -> BaseObservable.notifyPropertyChanged 

ModelDao
  -> insert
     update
     delete
    -> notify ModelObservable/ContentProvider
     
  -> query => Model
  
ModelTrans <- Model
  => ModelObservable

ContentProvider <- ModelDao._allChanges
  -> insert
  -> update
  -> delete
  
  -> query -> ModelDao.query -> Cursor => Model
```

## RecyclerAdapter
```java
DDCursorRecyclerAdapter
- onCreateViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent)
- onBindViewHolder(VH viewHolder, Cursor cursor)
- swapCursor(Cursor newCursor)

DDCursorLoaderRecyclerAdapter <- DDCursorRecyclerAdapter
- DDCursorLoaderRecyclerAdapter(Context context, Uri uri, String[] projection, String selection,
                                String[] selectionArgs, String sortOrder)
- onLoadFinished -> swapCursor

DDViewBindingCursorLoaderAdapter <- DDCursorLoaderRecyclerAdapter
- itemLayoutSelector(position -> {}) //get the layout_res for each item
- itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {}) //bind view with cursor data

- onItemClick((itemView, position) -> {})
```

## TODO
- [ ] potential memory leak on ModelObservable
- [ ] DDViewBindingCursorLoaderAdapter.onItemClick position choose
  
