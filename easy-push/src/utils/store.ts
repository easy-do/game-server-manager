import React from 'react'
import { MobXProviderContext } from 'mobx-react'

export default function useStores(storeName?: string) {
  const rootStore = React.useContext(MobXProviderContext)
  return storeName ? rootStore[storeName] : rootStore
}
