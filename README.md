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
    ->notify Cursor,notifyChange(@NonNull Uri uri, @Nullable ContentObserver observer)
  -> query -> ModelDao.query -> Cursor,setNotificationUri(ContentResolver cr, Uri uri) => Model
```

## RecyclerAdapter
```java
DDCursorRecyclerAdapter
+ public abstract VH createViewHolder(LayoutInflater inflater, int viewType, ViewGroup parent)
+ public abstract void bindViewHolder(VH viewHolder, Cursor cursor)
+ swapCursor(Cursor newCursor)

DDCursorLoaderRecyclerAdapter <- DDCursorRecyclerAdapter
+ DDCursorLoaderRecyclerAdapter(Context context, Uri uri, String[] projection, String selection,
                                String[] selectionArgs, String sortOrder)
+ onLoadFinished -> swapCursor

DDBindableCursorLoaderRVAdapter <- DDCursorLoaderRecyclerAdapter
+ ItemViewHolderCreator((inflater, viewType, parent) -> {}) //create viewholder
+ ItemLayoutSelector((position, cursor) -> {})//get the layout_res for each item
+ ItemViewDataBindingVariableAction((viewDataBinding, cursor) -> {}) //bind view with cursor data

+ onItemClickListener((itemView, position) -> {})
+ onItemSubViewClickListener(layoutRes, (viewholder, v, position) -> {})

DDBindableCursorLoaderRVHeaderAdapter <- DDBindableCursorLoaderRVAdapter
//add a header in adapter
+ HeaderViewHolderCreator((inflater, viewType, parent) -> {})
+ HeaderViewDataBindingVariableAction(viewDataBinding -> {})

DDBindableViewHolder implements IBindableViewHolder 
```

## TODO
- [ ] potential memory leak on ModelObservable
  
