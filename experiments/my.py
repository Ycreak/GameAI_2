import pandas as pd

df = pd.read_csv('e-tweaking.csv', sep=',')

print(df)

print(df.idxmax(axis=1).value_counts())
